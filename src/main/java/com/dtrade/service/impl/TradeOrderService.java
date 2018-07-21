package com.dtrade.service.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.*;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kudelin on 6/27/17.
 */
@Transactional
@Service
public class TradeOrderService  implements ITradeOrderService{

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    public final static BigDecimal ZERO_VALUE = new BigDecimal("0.00");
    public final static BigDecimal MINUS_ONE_VALUE = new BigDecimal("-1.00");

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private IStockService stockService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IBookOrderService bookOrderService;

    @Autowired
    private IStockActivityService stockActivityService;

    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private BalanceService balanceService;

    private TransactionTemplate transactionTemplate;

    private ExecutorService executor = Executors.newFixedThreadPool(25);

    //TODO add paging
    @Override
    public List<TradeOrder> findAll() {
        return tradeOrderRepository.findAll();
    }

    @Override
    public List<TradeOrder> rereadTradeOrders(Long[] ids){
        if(ids.length==0){
            return null;
        }
        List<Long> tradeOrdersIds = Arrays.asList(ids);
        return tradeOrderRepository.findAll(tradeOrdersIds);
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

            TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
            tradeOrderDTO.setId(to.getId());
            tradeOrderDTO.setAmount(to.getAmount());
            tradeOrderDTO.setInitialAmount(to.getInitialAmount());
            tradeOrderDTO.setPrice(to.getPrice());
            tradeOrderDTO.setExecutionDate(to.getExecutionDate());
            tradeOrderDTO.setTradeOrderDirection(to.getTradeOrderDirection());

            tradeOrderDTOS.add(tradeOrderDTO);
        }
        return tradeOrderDTOS;
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

                        Runnable r = () -> quotesService.issueQuote(pair);
                        executor.execute(r);

                        logger.debug("EXECUTING TRADE PAIR");

                        long start = System.currentTimeMillis();
                        executeTradeOrders(pair);
                        logger.debug("execute trade time: {}", (System.currentTimeMillis() - start));
                    }
                });
            }

           //System.out.println("Main execuiton: " + (System.currentTimeMillis() - start1));

        });
    }



    @Override
    public List<TradeOrder> getHistoryTradeOrders(Long diamondId) {
        Diamond diamond = diamondService.find(diamondId);
        return tradeOrderRepository.getHistoryTradeOrders(diamond, new PageRequest(0, 23));
    }

    @Override
    public Page<TradeOrder> getHistoryTradeOrdersByAccount(Integer pageNumber){
        Account account = accountService.getStrictlyLoggedAccount();
        return tradeOrderRepository.getHistoryTradeOrdersForAccount(account, new PageRequest(pageNumber, 10));
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

        return tradeOrderRepository.getLiveTradeOrdersByAccount(account, new PageRequest(pageNumber, 10));
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

        accountService.checkCurrentAccount(tradeOrder.getAccount());

        //TODO market price can be problematic
        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {
            Account account = accountService.find(tradeOrder.getAccount().getId());
            BigDecimal actualBalance =  balanceService.getActualBalance(tradeOrder.getDiamond().getCurrency(), account);
            BigDecimal cash = tradeOrder.getPrice().multiply(tradeOrder.getAmount());

            if(actualBalance.subtract(cash).compareTo(BigDecimal.ZERO) < 0){
                throw new TradeException("Already opened too many buy orders!");
            }
        }

        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)){
            Stock stock = stockService.getSpecificStock(tradeOrder.getAccount(), tradeOrder.getDiamond());
            BigDecimal openAmount = stock.getStockInTrade();
            if(stock.getAmount().subtract(openAmount).subtract(tradeOrder.getAmount()).compareTo(BigDecimal.ZERO) < 0){
                throw new TradeException("Already opened too many sell orders!");
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

        TradeOrder realOrder = new TradeOrder();
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

        realOrder = tradeOrderRepository.save(realOrder);

        balanceService.updateOpenSum(realOrder.getDiamond().getCurrency(), account,
                tradeOrder.getAmount().multiply(tradeOrder.getPrice()));

        stockService.updateStockInTrade(tradeOrder, account, tradeOrder.getDiamond(), tradeOrder.getAmount());

        bookOrderService.addNew(realOrder);

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
                .multiply(tradeOrder.getPrice()).multiply(MINUS_ONE_VALUE));

        stockService.updateStockInTrade(tradeOrder, tradeOrder.getAccount(), tradeOrder.getDiamond(),
                tradeOrder.getAmount().multiply(MINUS_ONE_VALUE));

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
                .multiply(tradeOrder.getPrice()).multiply(MINUS_ONE_VALUE));

        stockService.updateStockInTrade(tradeOrder, tradeOrder.getAccount(), tradeOrder.getDiamond(),
                tradeOrder.getAmount().multiply(MINUS_ONE_VALUE));

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


        /*
        if(buyOrder.getAmount().equals(new BigDecimal("0.00"))){
            return false;
        }

        if(sellOrder.getAmount().equals(new BigDecimal("0.00"))){
            return false;
        }*/

        return false;
    }

    //buy - sell
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = NotEnoughMoney.class)
    public void executeTradeOrders(Pair<TradeOrder, TradeOrder> pair) {

//        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//            @Override
//            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {
                    /*
                        1) Simple example of market order should be produced
                        1a) We assume that on market orders exists
                        2) Take sellers(!) stock and transfer to buyer
                        3) Transfer money
                        4) Write all activities
                        5) work only during one minute
                     */

                    //System.out.println("1.1");
                    long start = System.currentTimeMillis();

                    TradeOrder buyOrder = tradeOrderRepository.findOne(pair.getFirst().getId());
                    TradeOrder sellOrder = tradeOrderRepository.findOne(pair.getSecond().getId());
                    //System.out.println("1.2");
                    if (buyOrder==null || sellOrder == null){
                        return;
                    }
                   // System.out.println("1.3");

                   // Hibernate.initialize(buyOrder.getDiamond());
                   // Hibernate.initialize(sellOrder.getDiamond());

                    if (!buyOrder.getDiamond().equals(sellOrder.getDiamond())) {
                        return;
                    }
                   // System.out.println("1.4");

                    if (!(buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        return;
                    }

                   // System.out.println("1.5");

                    if (!(sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        return;
                    }
                   // System.out.println("1.6");

                    if (!checkIfCanExecute(pair)) {
                        return;
                    }

                   // System.out.println("1.7");

                    Account buyAccount = buyOrder.getAccount();
                    Account sellAccount = sellOrder.getAccount();

                    /*
                    Decided that buyer and seller can be the same
                    if (buyAccount.equals(sellAccount)) {
                        return;
                    }*/

                  //  System.out.println("1.8");

                    //Decided to buy side to prevail on sell side
                    BigDecimal orderPrice = definePrice(sellOrder, buyOrder);

                    BigDecimal buyAmount = buyOrder.getAmount();
                    BigDecimal sellAmount = sellOrder.getAmount();

                    BigDecimal realAmount = buyAmount.min(sellAmount);

                    Stock buyStock = stockService.getSpecificStock(buyAccount, buyOrder.getDiamond());
                    Stock sellStock = stockService.getSpecificStock(sellAccount, sellOrder.getDiamond());

                   // System.out.println("1.9");
                    //seller don't have enough stocks
                    if (sellStock.getAmount().compareTo(realAmount) < 0) {
                        //TODO notify user
                        logger.error("Not enough stocks at {}" + sellStock);
                        rejectTradeOrder(sellOrder);
                        return;
                        // throw new TradeException("Not enough stocks at " + sellStock);
                    }

                   // System.out.println("1.10");

                    BigDecimal cash = realAmount.multiply(orderPrice);

                    long startActivity = System.currentTimeMillis();
                    try {
                        balanceActivityService.createBalanceActivities(buyAccount, sellAccount, cash, buyOrder, sellOrder);
                    }catch (TradeException e){
                        //TODO notify user
                        logger.error("Rejecting {} because of exception {}", buyOrder, e);
                        rejectTradeOrder(buyOrder);
                        return;
                    }

                   // System.out.println("1.11");

                    logger.debug(" BALANCE ACTIVITY TIME: " + (System.currentTimeMillis() - startActivity));

                    buyStock.setAmount(buyStock.getAmount().add(realAmount));
                    sellStock.setAmount(sellStock.getAmount().subtract(realAmount));

                    //System.out.println("1.12");
                    stockActivityService.createStockActivity(buyOrder, sellOrder,
                            cash, orderPrice, realAmount);

                    buyOrder.setAmount(buyOrder.getAmount().subtract(realAmount));
                    sellOrder.setAmount(sellOrder.getAmount().subtract(realAmount));

                    buyOrder.setExecutionSum(buyOrder.getExecutionSum().add(cash));
                    sellOrder.setExecutionSum(sellOrder.getExecutionSum().add(cash));

                   // System.out.println("1.13");
                    checkIfExecuted(buyOrder);
                    checkIfExecuted(sellOrder);

                    stockService.updateStockInTrade(sellOrder, sellAccount, sellOrder.getDiamond(),
                            sellOrder.getAmount().multiply(MINUS_ONE_VALUE));

                    stockService.save(buyStock);
                    stockService.save(sellStock);

                   // System.out.println("1.14");
                    tradeOrderRepository.save(buyOrder);
                    tradeOrderRepository.save(sellOrder);

                    balanceService.updateOpenSum(buyOrder, buyAccount, cash.multiply(MINUS_ONE_VALUE));

                   // System.out.println("1.15");
                    accountService.save(buyAccount);
                    accountService.save(sellAccount);

                    //System.out.println("1.17");
                    long end = System.currentTimeMillis() - start;

                    logger.debug("SUC EXEC TIME: " + end);
                }catch (Exception e){
                    e.printStackTrace();
                }
//            }
//        });
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
        //TODO think about time solution
        return 0;
//        Account account = accountService.getStrictlyLoggedAccount();
//        return tradeOrderRepository.getSellSumForMonthForAccount(account, );
    }

    @Override
    public long buySumForMonthForAccount() {
        return 0;

       // Account account = accountService.getStrictlyLoggedAccount();
       // return tradeOrderRepository.getBuySumForMonthForAccount();
    }

    private void checkIfExecuted(TradeOrder tradeOrder){
        if (tradeOrder.getAmount().equals(ZERO_VALUE)) {
            bookOrderService.remove(tradeOrder);
            tradeOrder.setTraderOrderStatus(TraderOrderStatus.EXECUTED);
            setExecutionDate(tradeOrder);
        } else {
            tradeOrder.setTraderOrderStatus(TraderOrderStatus.IN_MARKET);
            bookOrderService.update(tradeOrder);
        }
    }

    private void setExecutionDate(TradeOrder tradeOrder){
        tradeOrder.setExecutionDate(System.currentTimeMillis());
    }
}
