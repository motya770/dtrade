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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
import java.util.concurrent.*;

/**
 * Created by kudelin on 6/27/17.
 */
@Transactional
@Service
public class TradeOrderService  implements ITradeOrderService{

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    public final static BigDecimal ZERO_VALUE = BigDecimal.ZERO;
    public final static BigDecimal MINUS_ONE_VALUE = new BigDecimal("-1.00");

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IBookOrderService bookOrderService;

    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private BalanceService balanceService;

    private ScheduledExecutorService historyCasheExecutor;

    private ConcurrentHashMap<Long, List<TradeOrder>> historyOrders = new ConcurrentHashMap<>();

    @EventListener(ContextRefreshedEvent.class)
    public void init(){

        historyCasheExecutor = Executors.newScheduledThreadPool(2);

        historyCasheExecutor.scheduleWithFixedDelay(()->{
            loadHistoryOrders();
        }, 1_000, 20, TimeUnit.MILLISECONDS);
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
        return tradeOrderDTO;
    }

    @Override
    public void calculateTradeOrders(){

        //logger.debug("CALCULATING TRADE ORDERS");

        bookOrderService.getBookOrders().entrySet().parallelStream().forEach((entry)->{

            long start1 = System.currentTimeMillis();

            List<Pair<TradeOrder, TradeOrder>> pairs = bookOrderService.find10Closest(entry.getKey());
            if(pairs!=null && pairs.size()>0){
                pairs.forEach(pair->{
                    if(checkIfCanExecute(pair)) {

                        Runnable quoteRunnable = () -> quotesService.issueQuote(pair);
                        executor.execute(quoteRunnable);
                        logger.debug("EXECUTING TRADE PAIR");

                        long start = System.currentTimeMillis();
                       // Runnable pairRunnable = () -> executeTradeOrders(pair);
                       // executor.execute(pairRunnable);

                       // executor.schedule(pairRunnable, getRandomDelay(), TimeUnit.MILLISECONDS);
                        transactionTemplate.execute(status -> {
                            executeTradeOrders(pair);
                            return status;
                        });


                        logger.debug("execute trade time: {}", (System.currentTimeMillis() - start));
                    }
                });
            }

           //System.out.println("Main execuiton: " + (System.currentTimeMillis() - start1));

        });
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
        return tradeOrderRepository.getLiveTradeOrders();
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
            BigDecimal actualBalance =  balanceService.getActualBalance(diamond.getBaseCurrency(), account);
            BigDecimal cash = tradeOrder.getPrice().multiply(tradeOrder.getAmount());

            if(actualBalance.subtract(cash).compareTo(BigDecimal.ZERO) < 0){
                throw new TradeException("Already opened too many buy orders for: " + account.getMail()  +  "!");
            }
        }

        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)){

            BigDecimal actualBalance =  balanceService.getActualBalance(diamond.getBaseCurrency(), account);
            BigDecimal amount = tradeOrder.getAmount();

            if(actualBalance.subtract(amount).compareTo(BigDecimal.ZERO) < 0){
                throw new TradeException("Already opened too many sell orders for: " + account.getMail()  +  "!");
            }
        }
    }


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

        realOrder.setPrice(tradeOrder.getPrice());
        realOrder.setTradeOrderDirection(tradeOrder.getTradeOrderDirection());
        realOrder.setTraderOrderStatus(TraderOrderStatus.CREATED);
        realOrder.setCreationDate(System.currentTimeMillis());
        realOrder.setTradeOrderType(tradeOrder.getTradeOrderType());
        realOrder.setExecutionSum(new BigDecimal("0.00"));

        logger.debug("before save {}", tradeOrder);

        TradeOrder order = tradeOrderRepository.save(realOrder);
        Diamond diamond  = diamondService.find(order.getDiamond().getId());

        System.out.println("Diamond: " + diamond);
        System.out.println("Currency: " +  diamond.getCurrency());

        balanceService.updateOpenSum(tradeOrder, account,
                    order.getAmount().multiply(order.getPrice()), order.getAmount());

        bookOrderService.addNew(order);

        logger.debug("Open Trade time {}", (System.currentTimeMillis() - start));
        return realOrder;
    }

    private void defineMarketPrice(TradeOrder tradeOrder){
        if(tradeOrder.getTradeOrderType().equals(TradeOrderType.MARKET)) {

            Pair<Diamond, Pair<BigDecimal, BigDecimal>> spread = bookOrderService.getSpread(tradeOrder.getDiamond());

            if(spread == null) {
                logger.info("Can't define market price because spread is empty.");
                tradeOrder.setPrice(new BigDecimal("100"));
            }else{
                if (tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {
                    // for buy order we take sell price
                    tradeOrder.setPrice(spread.getSecond().getSecond());

                } else if (tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)) {
                    // for sell order we take buy price
                    tradeOrder.setPrice(spread.getSecond().getFirst());
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

        bookOrderService.remove(tradeOrder);

        balanceService.updateOpenSum(tradeOrder, tradeOrder.getAccount(), tradeOrder.getAmount()
                .multiply(tradeOrder.getPrice()).multiply(MINUS_ONE_VALUE), tradeOrder.getAmount().multiply(MINUS_ONE_VALUE));

        return tradeOrder;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public TradeOrder rejectTradeOrder(TradeOrder tradeOrder) {
        if(tradeOrder==null){
            throw new TradeException("TradeOrder is null");
        }

        tradeOrder = tradeOrderRepository.getOne(tradeOrder.getId());

        if(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED)
                || tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CANCELED))
        {
            throw new TradeException("Can't reject trader order with status " + tradeOrder.getTraderOrderStatus());
        }

        tradeOrder.setTraderOrderStatus(TraderOrderStatus.REJECTED);

        tradeOrder = tradeOrderRepository.save(tradeOrder);

        bookOrderService.remove(tradeOrder);

        balanceService.updateOpenSum(tradeOrder, tradeOrder.getAccount(), tradeOrder.getAmount()
                .multiply(tradeOrder.getPrice()).multiply(MINUS_ONE_VALUE), tradeOrder.getAmount().multiply(MINUS_ONE_VALUE));

        return tradeOrder;
    }

    @Override
    public boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair) {

        if(pair==null){
            return false;
        }

        TradeOrder buyOrder = pair.getFirst();
        TradeOrder sellOrder = pair.getSecond();

        if(buyOrder==null || sellOrder == null){
            return false;
        }

        if(buyOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return true;
        }

        if(sellOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return true;
        }

        if(buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0){
            return true;
        }

        return false;
    }

    //buy - sell
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = NotEnoughMoney.class)
    public void executeTradeOrders(Pair<TradeOrder, TradeOrder> pair) {

                try {
                    /*
                        1) Simple example of market order should be produced
                        1a) We assume that on market orders exists
                        2) Take sellers(!) stock and transfer to buyer
                        3) Transfer money
                        4) Write all activities
                        5) work only during one minute
                     */

                    System.out.println("1.1");
                    long start = System.currentTimeMillis();

                    TradeOrder buyOrder = tradeOrderRepository.findById(pair.getFirst().getId()).orElse(null);
                    TradeOrder sellOrder = tradeOrderRepository.findById(pair.getSecond().getId()).orElse(null);
                    System.out.println("1.2");
                    if (sellOrder==null){
                        bookOrderService.remove(sellOrder);
                        return;
                    }

                    if (buyOrder==null){
                        bookOrderService.remove(buyOrder);
                        return;
                    }

                    System.out.println("1.3");
                    System.out.println("1.3");

                    if (!buyOrder.getDiamond().equals(sellOrder.getDiamond())) {
                        throw new TradeException("Something wrong with COINS pairs.");
                    }

                    System.out.println("1.4");

                    System.out.println("1.4.1: status b: " +  buyOrder.getTraderOrderStatus() + " " + buyOrder.getId());
                    System.out.println("1.4.3: status s: " +  sellOrder.getTraderOrderStatus() + " " + sellOrder.getId());

                    if (!(buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        bookOrderService.remove(buyOrder);
                        return;
                    }

                    System.out.println("1.5");

                    if (!(sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        bookOrderService.remove(sellOrder);
                        return;
                    }

                    System.out.println("1.6");

                    if (!checkIfCanExecute(pair)) {
                        return;
                    }

                    System.out.println("1.7");

                    Account buyAccount = buyOrder.getAccount();
                    Account sellAccount = sellOrder.getAccount();

                    //Decided that buyer and seller can be the same
//                    if (buyAccount.getId().equals(sellAccount.getId())) {
//                        return;
//                    }

                    System.out.println("1.8");

                    //Decided to buy side to prevail on sell side
                    BigDecimal orderPrice = definePrice(sellOrder, buyOrder);

                    BigDecimal buyAmount = buyOrder.getAmount();
                    BigDecimal sellAmount = sellOrder.getAmount();
                    BigDecimal realAmount = buyAmount.min(sellAmount);

                    Currency currency = buyOrder.getDiamond().getCurrency();

                    Balance buyBalance = balanceService.getBalance(currency, buyAccount);
                    Balance sellBalance = balanceService.getBalance(currency, sellAccount);

                    System.out.println("1.9");
                    //seller don't have enough stocks
                    if (buyBalance.getAmount().compareTo(realAmount) < 0) {
                        //TODO notify user
                        logger.error("Not enough coins at {}" + buyBalance);
                        rejectTradeOrder(sellOrder);
                        return;
                    }

                    if (sellBalance.getAmount().compareTo(realAmount) < 0) {
                        //TODO notify user
                        logger.error("Not enough coins at {}" + sellBalance);
                        rejectTradeOrder(sellOrder);
                        return;
                    }

                    System.out.println("1.10");

                    BigDecimal cash = realAmount.multiply(orderPrice);

                    long startActivity = System.currentTimeMillis();
                    try {
                        System.out.println("1.10.1 " + Thread.currentThread().getName());
                        balanceActivityService.createBalanceActivities(buyAccount, sellAccount, buyOrder,
                                sellOrder, realAmount, orderPrice);
                    }catch (TradeException e){
                        //TODO notify user
                        logger.error("Rejecting {} because of exception {}", buyOrder, e);
                        rejectTradeOrder(buyOrder);
                        return;
                    }

                    System.out.println("1.11");

                    logger.debug(" BALANCE ACTIVITY TIME: " + (System.currentTimeMillis() - startActivity));

                    System.out.println("1.12");

                    buyOrder.setAmount(buyOrder.getAmount().subtract(realAmount));
                    sellOrder.setAmount(sellOrder.getAmount().subtract(realAmount));

                    buyOrder.setExecutionSum(buyOrder.getExecutionSum().add(cash));
                    sellOrder.setExecutionSum(sellOrder.getExecutionSum().add(cash));

                    System.out.println("1.13");
                    checkIfExecuted(buyOrder);
                    checkIfExecuted(sellOrder);

                    System.out.println("1.14");
                    tradeOrderRepository.save(buyOrder);
                    tradeOrderRepository.save(sellOrder);

                    System.out.println("1.15");
                    long end = System.currentTimeMillis() - start;

                    logger.debug("SUC EXEC TIME: " + end);

                    System.out.println("exec1: "
                            + sellOrder.getId() + " "
                            + sellOrder.getTraderOrderStatus() + ", "
                            + buyOrder.getId() + ":"
                            + buyOrder.getTraderOrderStatus());
                    System.out.println("exec2:  " + sellOrder.getDiamond().getName() +  " " + realAmount + " " + orderPrice);
                }catch (Exception e){
                    e.printStackTrace();
                }
    }

    private BigDecimal definePrice(TradeOrder sellOrder, TradeOrder buyOrder){

        if(buyOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return sellOrder.getPrice();
        }

        if(sellOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return buyOrder.getPrice();
        }

        return buyOrder.getPrice();
    }

    @Override
    public long sellSumForMonthForAccount() {
        return 0;
    }

    @Override
    public long buySumForMonthForAccount() {
        return 0;
    }

    private void checkIfExecuted(TradeOrder order){
       // System.out.println("checking if executed " + order.getAmount().setScale(8));
        if (order.getAmount().compareTo(ZERO_VALUE)==0) {
            System.out.println("EXECUTED: " + order.getId());
            order.setTraderOrderStatus(TraderOrderStatus.EXECUTED);
            setExecutionDate(order);
            bookOrderService.remove(order);
        } else {
            order.setTraderOrderStatus(TraderOrderStatus.IN_MARKET);
            bookOrderService.update(order);
        }
    }

    private void setExecutionDate(TradeOrder tradeOrder){
        tradeOrder.setExecutionDate(System.currentTimeMillis());
    }
}
