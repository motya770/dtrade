package com.dtrade.service.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.model.tradeorder.TraderOrderStatus;
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
/**
 * Created by kudelin on 6/27/17.
 */
@Transactional
@Service
public class TradeOrderService  implements ITradeOrderService{

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    public final static BigDecimal ZERO_VALUE = new BigDecimal("0.00");

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

    private TransactionTemplate transactionTemplate;

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

    @Override
    public BigDecimal getOpenedStocksAmount(Account account, Diamond diamond) {

        List<TradeOrder> tradeOrders = tradeOrderRepository.getSellTradesByAccountAndDiamond(account, diamond);
        BigDecimal sumStocks = new BigDecimal("0.00");
        for(TradeOrder order: tradeOrders){
            sumStocks = sumStocks.add(order.getAmount());
        }
        return sumStocks;
    }

    @Override
    public BigDecimal getAllOpenedTradesSum(Account account){
        List<TradeOrder> tradeOrders = tradeOrderRepository.getBuyOpenTradesByAccount(account);
        BigDecimal sum = new BigDecimal("0.00");
        for(TradeOrder order: tradeOrders){
            sum = sum.add(order.getPrice().multiply(order.getAmount()));
        }
        return sum;
    }

    /*
    @Override
    public BigDecimal getOpenedTradesSum(Account account, Diamond diamond) {
        //TODO fix performance
        List<TradeOrder> tradeOrders = tradeOrderRepository.getBuyOpenTradesByAccountAndDiamond(account, diamond);
        BigDecimal sum = new BigDecimal("0.00");
        for(TradeOrder order: tradeOrders){
            sum = sum.add(order.getPrice().multiply(order.getAmount()));
        }
        return sum;
    }*/

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
            tradeOrderDTO.setTradeOrderType(to.getTradeOrderType());

            tradeOrderDTOS.add(tradeOrderDTO);
        }
        return tradeOrderDTOS;
    }

    @Override
    public void calculateTradeOrders(){

        //logger.debug("CALCULATING TRADE ORDERS");

        bookOrderService.getBookOrders().entrySet().forEach((entry)->{

            long start1 = System.currentTimeMillis();

            /*
            Pair<TradeOrder, TradeOrder> pair = bookOrderService.findClosest(entry.getKey());
            if(pair!=null){
                    if(checkIfCanExecute(pair)) {

                        quotesService.issueQuote(pair);

                        logger.debug("EXECUTING TRADE PAIR");

                        long start = System.currentTimeMillis();
                        executeTradeOrders(pair);
                        logger.info("execute trade time: {}", (System.currentTimeMillis() - start));
                    }
            }*/


            List<Pair<TradeOrder, TradeOrder>> pairs = bookOrderService.find10Closest(entry.getKey());
            if(pairs!=null && pairs.size()>0){
                pairs.forEach(pair->{
                    if(checkIfCanExecute(pair)) {

                        quotesService.issueQuote(pair);

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
    public List<TradeOrder> getHistoryTradeOrders(Diamond diamond) {
        return tradeOrderRepository.getHistoryTradeOrders(diamond, new PageRequest(0, 23));
    }

    @Override
    public Page<TradeOrder> getHistoryTradeOrdersByAccount(Integer pageNumber){
        Account account = accountService.getStrictlyLoggedAccount();
        return tradeOrderRepository.getHistoryTradeOrdersForAccount(account, new PageRequest(pageNumber, 10));
    }

    @Override
    public List<TradeOrder> getLiveTradeOrders() {
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

        if(tradeOrder.getPrice()==null || (tradeOrder.getPrice().compareTo(ZERO_VALUE)<=0)){
            throw new TradeException("Can't create trade order because price is empty.");
        }

        if(tradeOrder.getPrice()==null ){
            throw new TradeException("Can't create trade order because price is empty.");
        }

        if(tradeOrder.getPrice().compareTo(ZERO_VALUE)<=0){
            throw new TradeException("Can't create trade order because price is less than 0.");
        }

        if(tradeOrder.getTradeOrderType()==null){
            throw new TradeException("Can't create trade order because trade order type is empty.");
        }

        accountService.checkCurrentAccount(tradeOrder.getAccount());

        if(tradeOrder.getTradeOrderType().equals(TradeOrderType.BUY)) {
            BigDecimal openedSum = getAllOpenedTradesSum(tradeOrder.getAccount());
            Account account = accountService.find(tradeOrder.getAccount().getId());
            BigDecimal balance = account.getBalance();
            BigDecimal cash = tradeOrder.getPrice().multiply(tradeOrder.getAmount());
            if(balance.subtract(openedSum).subtract(account.getFrozenBalance()).subtract(cash).compareTo(BigDecimal.ZERO) < 0){
                throw new TradeException("Already opened too many buy orders!");
            }
        }

        if(tradeOrder.getTradeOrderType().equals(TradeOrderType.SELL)){
            BigDecimal openAmount = getOpenedStocksAmount(tradeOrder.getAccount(), tradeOrder.getDiamond());
            Stock stock = stockService.getSpecificStock(tradeOrder.getAccount(), tradeOrder.getDiamond());
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

        validateFields(tradeOrder);

        TradeOrder realOrder = new TradeOrder();
        realOrder.setAmount(tradeOrder.getAmount());
        realOrder.setInitialAmount(tradeOrder.getAmount());
        realOrder.setDiamond(tradeOrder.getDiamond());
        realOrder.setAccount(tradeOrder.getAccount());
        realOrder.setPrice(tradeOrder.getPrice());
        realOrder.setTradeOrderType(tradeOrder.getTradeOrderType());
        realOrder.setTraderOrderStatus(TraderOrderStatus.CREATED);
        realOrder.setCreationDate(System.currentTimeMillis());

        logger.debug("before save {}", tradeOrder);


        realOrder = tradeOrderRepository.save(realOrder);

        bookOrderService.addNew(realOrder);

        logger.debug("Open Trade time {}", (System.currentTimeMillis() - start));
        return realOrder;
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

                    System.out.println("1.1");
                    long start = System.currentTimeMillis();

                    TradeOrder buyOrder = tradeOrderRepository.findOne(pair.getFirst().getId());
                    TradeOrder sellOrder = tradeOrderRepository.findOne(pair.getSecond().getId());
                    //System.out.println("1.2");
                    if (buyOrder==null || sellOrder == null){
                        return;
                    }
                    System.out.println("1.3");

                   // Hibernate.initialize(buyOrder.getDiamond());
                   // Hibernate.initialize(sellOrder.getDiamond());

                    if (!buyOrder.getDiamond().equals(sellOrder.getDiamond())) {
                        return;
                    }
                    System.out.println("1.4");

                    if (!(buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        return;
                    }

                    System.out.println("1.5");

                    if (!(sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        return;
                    }
                    System.out.println("1.6");

                    if (!checkIfCanExecute(pair)) {
                        return;
                    }

                    System.out.println("1.7");

                    Account buyAccount = buyOrder.getAccount();
                    Account sellAccount = sellOrder.getAccount();

                    /*
                    Decided that buyer and seller can be the same
                    if (buyAccount.equals(sellAccount)) {
                        return;
                    }*/

                    System.out.println("1.8");

                    BigDecimal buyPrice = buyOrder.getPrice();
                    BigDecimal buyAmount = buyOrder.getAmount();
                    BigDecimal sellAmount = sellOrder.getAmount();

                    BigDecimal realAmount = buyAmount.min(sellAmount);

                    Stock buyStock = stockService.getSpecificStock(buyAccount, buyOrder.getDiamond());
                    Stock sellStock = stockService.getSpecificStock(sellAccount, sellOrder.getDiamond());

                    System.out.println("1.9");
                    //seller don't have enouth stocks
                    if (sellStock.getAmount().compareTo(realAmount) < 0) {
                        //TODO notify user
                        logger.error("Not enougth stocks at {}" + sellStock);
                        rejectTradeOrder(sellOrder);
                        return;
                        // throw new TradeException("Not enougth stocks at " + sellStock);
                    }

                    System.out.println("1.10");

                    BigDecimal cash = realAmount.multiply(buyPrice);

                    long startActivity = System.currentTimeMillis();
                    try {
                        balanceActivityService.createBalanceActivities(buyAccount, sellAccount, cash, buyOrder, sellOrder);
                    }catch (TradeException e){
                        //TODO notify user
                        logger.error("Rejecting {} because of exception {}", buyOrder, e);
                        rejectTradeOrder(buyOrder);
                        return;
                    }

                    System.out.println("1.11");

                    logger.debug(" BALANCE ACTIVITY TIME: " + (System.currentTimeMillis() - startActivity));

                    buyStock.setAmount(buyStock.getAmount().add(realAmount));
                    sellStock.setAmount(sellStock.getAmount().subtract(realAmount));

                    //System.out.println("1.12");
                    stockActivityService.createStockActivity(buyOrder, sellOrder,
                            cash, realAmount);

                    buyOrder.setAmount(buyOrder.getAmount().subtract(realAmount));
                    sellOrder.setAmount(sellOrder.getAmount().subtract(realAmount));

                    System.out.println("1.13");
                    checkIfExecuted(buyOrder);
                    checkIfExecuted(sellOrder);

                    stockService.save(buyStock);
                    stockService.save(sellStock);

                    System.out.println("1.14");
                    tradeOrderRepository.save(buyOrder);
                    tradeOrderRepository.save(sellOrder);

                    System.out.println("1.15");
                    accountService.save(buyAccount);
                    accountService.save(sellAccount);

                    System.out.println("1.17");
                    long end = System.currentTimeMillis() - start;

                    logger.debug("SUC EXEC TIME: " + end);
                }catch (Exception e){
                    e.printStackTrace();
                }
//            }
//        });
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
