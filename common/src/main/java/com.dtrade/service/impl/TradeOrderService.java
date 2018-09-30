package com.dtrade.service.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.Const;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.*;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kudelin on 6/27/17.
 */
@Transactional
@Service
public class TradeOrderService  implements ITradeOrderService{

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private IRabbitService rabbitService;

    private ScheduledExecutorService historyCasheExecutor;

    private ConcurrentHashMap<Long, List<TradeOrder>> historyOrders = new ConcurrentHashMap<>();

    @EventListener(ContextRefreshedEvent.class)
    public void init(){

        historyCasheExecutor = Executors.newScheduledThreadPool(2);

        historyCasheExecutor.scheduleWithFixedDelay(()->{
            loadHistoryOrders();
        }, 1_000, 10, TimeUnit.MILLISECONDS);
    }

    private void loadHistoryOrders(){
        List<Diamond> diamonds = diamondService.getAvailable();
        diamonds.forEach(diamond -> {
            List<TradeOrder> tradeOrders =  getHistoryTradeOrders(diamond.getId());
            historyOrders.put(diamond.getId(), tradeOrders);
        });
    }

    @Override
    public List<TradeOrder> getHistoryTradeOrdersCashed(Long diamondId){
        return historyOrders.get(diamondId);
    }

    private TransactionTemplate transactionTemplate;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(25); //Executors.newFixedThreadPool(25);

    @Override
    public Page<TradeOrder> findAll(Integer pageNumber) {
        if(pageNumber==null){
            pageNumber=0;
        }
        return tradeOrderRepository.findAll(PageRequest.of(pageNumber, 50, Sort.Direction.DESC, Const.F_ID));
    }

    @Override
    public List<TradeOrder> rereadTradeOrders(Long[] ids){
        if(ids.length==0){
            return null;
        }
        List<Long> tradeOrdersIds = Arrays.asList(ids);
        return tradeOrderRepository.findAllById(tradeOrdersIds);
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    public List<TradeOrderDTO> getTradeOrderDTO(List<TradeOrder> tradeOrders) {

        List<TradeOrderDTO> tradeOrderDTOS = new ArrayList<>(tradeOrders.size());

        if(tradeOrders.size() == 0){
            return tradeOrderDTOS;
        }

        for(int i = 0; i < tradeOrders.size(); i++){
            TradeOrder to = tradeOrders.get(i);
            TradeOrderDTO tradeOrderDTO = convert(to);
            tradeOrderDTOS.add(tradeOrderDTO);
        }
        return tradeOrderDTOS;
    }

    public TradeOrderDTO convert(TradeOrder to){
        TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
        tradeOrderDTO.setId(to.getId());
        tradeOrderDTO.setAmount(to.getAmount());
        tradeOrderDTO.setInitialAmount(to.getInitialAmount());
        tradeOrderDTO.setPrice(to.getPrice());
        tradeOrderDTO.setExecutionDate(to.getExecutionDate());
        tradeOrderDTO.setTradeOrderDirection(to.getTradeOrderDirection());
        tradeOrderDTO.setCreationDate(to.getCreationDate());
        tradeOrderDTO.setDiamondId(to.getDiamond().getId());
        return tradeOrderDTO;
    }


    private int getRandomDelay(){
        Random r = new Random();
        int l = 20;
        int h = 110;
        int result = r.nextInt(h - l ) + l;

        //System.out.println("USING random r: " + result);
        return result;
    }

    @Override
    public List<TradeOrder> getHistoryTradeOrders(Long diamondId) {
        Diamond diamond = diamondService.find(diamondId);
        long oneDay = System.currentTimeMillis() -Duration.ofDays(1).toMillis();
        return tradeOrderRepository.getHistoryTradeOrders(diamond.getId(), oneDay);
    }

    @Override
    public Page<TradeOrder> getHistoryTradeOrdersByAccount(Integer pageNumber){
        Account account = accountService.getStrictlyLoggedAccount();
        return tradeOrderRepository.getHistoryTradeOrdersForAccount(account, PageRequest.of(pageNumber, 10));
    }

    @Override
    public List<TradeOrder> getLiveTradeOrders() {
        logger.info("CALLING getLiveTradeOrders");
        return tradeOrderRepository.getLiveTradeOrders(PageRequest.of(0, 2000));
    }

    @Override
    public Page<TradeOrder> getLiveTradeOrdersByAccount(Integer pageNumber){
        Account account = accountService.getCurrentAccount();
        if(account==null){
            return null;
        }

        if(pageNumber==null){
            pageNumber=0;
        }

        return tradeOrderRepository.getLiveTradeOrdersByAccount(account, PageRequest.of(pageNumber, 10));
    }

    @Override
    public void validateFields(TradeOrder tradeOrder){

        //TODO thing about exception and explanation and client validation
        if(tradeOrder.getAmount()==null ){
            throw new TradeException("Can't create trade order because amount value is empty.");
        }

        if(tradeOrder.getAmount().compareTo(ZERO_VALUE) <= 0){
             throw new TradeException("Can't create trade order because amount value is less than 0.");
        }

        if(tradeOrder.getDiamond()==null){
             throw new TradeException("Can't create trade order because diamond is empty.");
        }


        if(tradeOrder.getAccount()==null){
             throw new TradeException("Can't create trade order because account is empty.");
        }

        if(tradeOrder.getPrice()==null ){
             throw new TradeException("Can't create trade order because price is empty.");
        }

        if(tradeOrder.getPrice().compareTo(ZERO_VALUE)<=0){
             throw new TradeException("Can't create trade order because price is less than 0.");
        }


        if(tradeOrder.getTradeOrderDirection()==null){
            throw new TradeException("Can't create trade order because trade order type is empty.");
        }

        Account account = tradeOrder.getAccount();
        accountService.checkCurrentAccount(account);
        account =  accountService.find(account.getId());

        Diamond diamond = diamondService.find(tradeOrder.getDiamond().getId());
        //TODO market price can be problematic

        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {

            Balance balance =  balanceService.getBalance(diamond.getBaseCurrency(), account);

            if (balance.getAmount().compareTo(BigDecimal.ZERO)==0){
                throw new TradeException("Account " + account.getMail()  +  " don't have enough currency " + diamond.getBaseCurrency() + " . Please make deposit. ");
            }

            BigDecimal actualBalance = balance.getActualBalance();
            BigDecimal cash = tradeOrder.getPrice().multiply(tradeOrder.getAmount());
            if(actualBalance.subtract(cash).compareTo(BigDecimal.ZERO) < 0){
                throw new TradeException("Already opened too many buy orders for: " + account.getMail()  +  "!");
            }
        }

        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)){

            Balance balance =  balanceService.getBalance(diamond.getCurrency(), account);

            if (balance.getAmount().compareTo(BigDecimal.ZERO)==0){
                throw new TradeException("Account " + account.getMail()  +  " don't have enough currency " + diamond.getCurrency() + " . Please make deposit. ");
            }

            BigDecimal actualBalance = balance.getActualBalance();
            BigDecimal amount = tradeOrder.getAmount();
            if(actualBalance.subtract(amount).compareTo(BigDecimal.ZERO) < 0){
                throw new TradeException("Already opened too many sell orders for: " + account.getMail()  +  "!");
            }
        }
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public TradeOrder createTradeOrder(TradeOrder tradeOrder) {

        //TODO maybe we should price too.
        //TODO check account balance.
        //TODO freeze money (?)

        long start = System.currentTimeMillis();

        defineMarketPrice(tradeOrder);

        validateFields(tradeOrder);

        final TradeOrder realOrder = new TradeOrder();
        realOrder.setAmount(tradeOrder.getAmount());
        realOrder.setInitialAmount(tradeOrder.getAmount());
        realOrder.setDiamond(tradeOrder.getDiamond());

        Account account = accountService.find(tradeOrder.getAccount().getId());
        realOrder.setAccount(account);

        realOrder.setPrice(tradeOrder.getPrice().setScale(8, BigDecimal.ROUND_HALF_UP));
        realOrder.setTradeOrderDirection(tradeOrder.getTradeOrderDirection());
        realOrder.setTraderOrderStatus(TraderOrderStatus.CREATED);
        realOrder.setCreationDate(System.currentTimeMillis());
        realOrder.setTradeOrderType(tradeOrder.getTradeOrderType());
        realOrder.setExecutionSum(new BigDecimal("0.00"));

        logger.debug("before save {}", tradeOrder);

        TradeOrder order = tradeOrderRepository.save(realOrder);

        afterTradeOrderCreation(order, account);
        return order;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public TradeOrder afterTradeOrderCreation(TradeOrder order, Account account) {

        order = tradeOrderRepository.findById(order.getId()).orElseThrow(()->new TradeException("TradeOrder is not created"));

        Diamond diamond  = diamondService.find(order.getDiamond().getId());

        diamondService.validateDiamondCanTrade(diamond);

        System.out.println("Diamond: " + diamond);
        System.out.println("Currency: " +  diamond.getCurrency());

        balanceService.updateOpenSum(order, account,
                order.getAmount().multiply(order.getPrice()), order.getAmount());

        bookOrderServiceProxy.addNew(order);

        rabbitService.tradeOrderCreated(convert(order));

        //logger.debug("Open Trade time {}", (System.currentTimeMillis() - start));
        return order;
    }


    private void defineMarketPrice(TradeOrder tradeOrder){
        if(tradeOrder.getTradeOrderType().equals(TradeOrderType.MARKET)) {

            Pair<Diamond, Pair<BigDecimal, BigDecimal>> spread = bookOrderServiceProxy.getSpread(tradeOrder.getDiamond());

            if(spread == null) {
                logger.info("Can't define market price because spread is empty.");
                tradeOrder.setPrice(new BigDecimal("100").setScale(8, BigDecimal.ROUND_HALF_UP));
            }else{
                if (tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {
                    // for buy order we take sell price
                    tradeOrder.setPrice(spread.getSecond().getSecond().setScale(8, BigDecimal.ROUND_HALF_UP));

                } else if (tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)) {
                    // for sell order we take buy price
                    tradeOrder.setPrice(spread.getSecond().getFirst().setScale(8, BigDecimal.ROUND_HALF_UP));
                } else {
                    throw new TradeException("Unexpected behavior");
                }
            }
        }
    }

    @Override
    public TradeOrder cancelTradeOrder(TradeOrder tradeOrder) {
        if(tradeOrder==null){
            throw new TradeException("TradeOrder is null");
        }

        tradeOrder = tradeOrderRepository.getOne(tradeOrder.getId());
        accountService.checkCurrentAccount(tradeOrder.getAccount());

        if(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED)
                || tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CANCELED))
        {
            throw new TradeException("Can't cancel trader order with status " + tradeOrder.getTraderOrderStatus());
        }


        tradeOrder.setTraderOrderStatus(TraderOrderStatus.CANCELED);

        tradeOrder = tradeOrderRepository.save(tradeOrder);

        bookOrderServiceProxy.remove(tradeOrder);

        balanceService.updateOpenSum(tradeOrder, tradeOrder.getAccount(), tradeOrder.getAmount()
                .multiply(tradeOrder.getPrice()).multiply(MINUS_ONE_VALUE), tradeOrder.getAmount().multiply(MINUS_ONE_VALUE));

        return tradeOrder;
    }

    @Override
    public BigDecimal definePrice(TradeOrder sellOrder, TradeOrder buyOrder){

        if(buyOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return sellOrder.getPrice();
        }

        if(sellOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return buyOrder.getPrice();
        }

        return buyOrder.getPrice();
    }

    public static void main(String... args){

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        List<Boolean> buyResults = new ArrayList<>(10);
        List<Boolean> sellResults  = new ArrayList<>(10);

        for(int i=0; i<10; i++){
            for(int j = 0; j<10; j++){
                System.out.println("ij " + i + " " + j);
                if(j==5){
                    break;
                }
            }
            System.out.println("after first break");
            if(i==3){
                break;
            }
        }
    }
}
