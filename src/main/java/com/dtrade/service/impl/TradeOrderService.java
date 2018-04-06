package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
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

    private BigDecimal zeroValue = new BigDecimal("0.00");

    private TransactionTemplate transactionTemplate;




    //TODO add paging
    @Override
    public List<TradeOrder> findAll() {
        return tradeOrderRepository.findAll();
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    public void calculateTradeOrders(){

        logger.debug("CALCULATING TRADE ORDERS");

        bookOrderService.getBookOrders().entrySet().forEach((entry)->{
           // System.out.println("ONE: " + entry.getKey() + " " + entry.getValue().getSell().size() + " " + entry.getValue().getBuy().size());
        });

        bookOrderService.getBookOrders().entrySet().forEach((entry)->{
            Pair<TradeOrder, TradeOrder> pair = bookOrderService.findClosest(entry.getKey());

            quotesService.issueQuote(pair);

            if(checkIfCanExecute(pair)) {

                logger.debug("EXECUTING TRADE PAIR");
                executeTradeOrders(pair);
            }
        });
    }

    @Override
    public List<TradeOrder> getHistoryTradeOrders() {
        return tradeOrderRepository.getHistoryTradeOrders(new PageRequest(0, 30));
    }

    @Override
    public List<TradeOrder> getHistoryTradeOrdersByAccount(){
        Account account = accountService.getStrictlyLoggedAccount();
        return tradeOrderRepository.getHistoryTradeOrdersForAccount(account, new PageRequest(0, 20));
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

        return tradeOrderRepository.getLiveTradeOrdersByAccount(account, new PageRequest(pageNumber, 12));
    }

    @Override
    public boolean fieldsNotEmpty(TradeOrder tradeOrder){

        //TODO thing about exception and explanation and client validation
        if(tradeOrder.getAmount()==null || (tradeOrder.getAmount().compareTo(zeroValue) <= 0)){
            return false;
        }

        if(tradeOrder.getDiamond()==null){
            return false;
        }

        if(tradeOrder.getAccount()==null){
            return false;
        }

        if(tradeOrder.getPrice()==null || (tradeOrder.getPrice().compareTo(zeroValue)<=0)){
            return false;
        }

        if(tradeOrder.getTradeOrderType()==null){
            return false;
        }

        return true;
    }

    @Override
    public TradeOrder createTradeOrder(TradeOrder tradeOrder) {

        //TODO maybe we should price too.
        //TODO check account balance.
        //TODO freeze money (?)

        if(!fieldsNotEmpty(tradeOrder)){
            throw new TradeException("Can't create trade order because some fields is empty");
        }

        accountService.checkCurrentAccount(tradeOrder.getAccount());

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

                    long start = System.currentTimeMillis();

                    TradeOrder buyOrder = tradeOrderRepository.findOne(pair.getFirst().getId());
                    TradeOrder sellOrder = tradeOrderRepository.findOne(pair.getSecond().getId());

                    if (!buyOrder.getDiamond().equals(sellOrder.getDiamond())) {
                        return;
                    }

                    if (!(buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        return;
                    }

                    if (!(sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                            || sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                        return;
                    }

                    if (!checkIfCanExecute(pair)) {
                        return;
                    }

                    Account buyAccount = buyOrder.getAccount();
                    Account sellAccount = sellOrder.getAccount();

                    /*
                    Decided that buyer and seller can be the same
                    if (buyAccount.equals(sellAccount)) {
                        return;
                    }*/


                    BigDecimal buyPrice = buyOrder.getPrice();
                    BigDecimal buyAmount = buyOrder.getAmount();
                    BigDecimal sellAmount = sellOrder.getAmount();

                    BigDecimal realAmount = buyAmount.min(sellAmount);

                    Stock buyStock = stockService.getSpecificStock(buyAccount, buyOrder.getDiamond());
                    Stock sellStock = stockService.getSpecificStock(sellAccount, sellOrder.getDiamond());

                    //seller don't have enouth stocks
                    if (sellStock.getAmount().compareTo(realAmount) < 0) {
                        throw new TradeException("Not enougth stocks at " + sellStock);
                    }

                    BigDecimal cash = realAmount.multiply(buyPrice);

                    long startActivity = System.currentTimeMillis();
                    balanceActivityService.createBalanceActivities(buyAccount, sellAccount, cash, buyOrder, sellOrder);
                    logger.debug(" BALANCE ACTIVITY TIME: " + (System.currentTimeMillis() - startActivity));

                    buyStock.setAmount(buyStock.getAmount().add(realAmount));
                    sellStock.setAmount(sellStock.getAmount().subtract(realAmount));

                    stockActivityService.createStockActivity(buyOrder, sellOrder,
                            cash, realAmount);

                    buyOrder.setAmount(buyOrder.getAmount().subtract(realAmount));
                    sellOrder.setAmount(sellOrder.getAmount().subtract(realAmount));

                    checkIfExecuted(buyOrder);
                    checkIfExecuted(sellOrder);

                    stockService.save(buyStock);
                    stockService.save(sellStock);

                    tradeOrderRepository.save(buyOrder);
                    tradeOrderRepository.save(sellOrder);

                    accountService.save(buyAccount);
                    accountService.save(sellAccount);

                    long end = System.currentTimeMillis() - start;

                    logger.debug("TIME: " + end);
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
        if (tradeOrder.getAmount().equals(zeroValue)) {
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
