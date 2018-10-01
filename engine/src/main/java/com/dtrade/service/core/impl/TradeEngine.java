package com.dtrade.service.core.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.model.tradeorder.TraderOrderStatus;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.*;
import com.dtrade.service.core.ITradeEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kudelin on 7/4/17.
 */
@Transactional
@Service
public class TradeEngine implements ITradeEngine {

    private static final Logger logger = LoggerFactory.getLogger(TradeEngine.class);

    private ScheduledExecutorService service;

    @Autowired
    private IBookOrderService bookOrderService;

    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private IRabbitService rabbitService;

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private IBalanceService balanceService;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    private TransactionTemplate transactionTemplate;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(25);

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    public void prepareAndLaunch() {
        Runnable runnable = () -> {
            tradeOrderService.getLiveTradeOrders().forEach(tradeOrder -> bookOrderService.addNew(tradeOrder));
            logger.info("Starting trade engine");
            launch();
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    // @EventListener(ContextRefreshedEvent.class)
    @Override
    public void launch(){

       service = Executors.newScheduledThreadPool(10);
       //TODO rewrite
       service.scheduleWithFixedDelay(()->{
           try{
              // System.out.println("IN THE LOOP!!!");
               calculateTradeOrders();
           }catch (Exception e){
               e.printStackTrace();
           }

       }, 1_000,  40, TimeUnit.MILLISECONDS);
    }

    @Transactional
    @Override
    public void calculateTradeOrders(){

        //logger.debug("CALCULATING TRADE ORDERS");

        bookOrderService.getBookOrders().entrySet().parallelStream().forEach((entry)->{

            //System.out.println("NEW THREAD");

            long start1 = System.currentTimeMillis();
            int exitCounter = 0;
            while (true) {
                //System.out.println("STRAT WHILE");
                Pair<TradeOrder, TradeOrder> buySell = bookOrderService.findClosest(entry.getKey());
                if(checkIfCanExecute(buySell)){

                    Runnable quoteRunnable = () -> quotesService.issueQuote(buySell);
                    executor.execute(quoteRunnable);

                    System.out.println("CAN EXECUTE " + entry.getKey());
                    transactionTemplate.execute(status -> {
                        return executeTradeOrders(buySell);
                    });
                }else {
                    break;
                }

                if(exitCounter==100){
                    break;
                }
                exitCounter++;
            }

        });
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
                .multiply(tradeOrder.getPrice()).multiply(ITradeOrderService.MINUS_ONE_VALUE),
                tradeOrder.getAmount().multiply(ITradeOrderService.MINUS_ONE_VALUE));

        return tradeOrder;
    }

    @Override
    public boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair) {

        if(pair==null){
            return false;
        }

        TradeOrder buyOrder = pair.getLeft();
        TradeOrder sellOrder = pair.getRight();

        if(buyOrder==null || sellOrder == null){
            System.out.println("wtf null");
            return false;
        }

        //buyOrder = tradeOrderRepository.findById(buyOrder.getId()).orElse(null);
        //sellOrder = tradeOrderRepository.findById(sellOrder.getId()).orElse(null);

//        if(buyOrder==null || sellOrder == null){
//            System.out.println("wtf null 2");
//            return false;
//        }

        if (buyOrder.getInitialAmount().compareTo(BigDecimal.ZERO) == 0){
            logger.error("trade order amount is null " + buyOrder.getId());
            System.out.println("wtf amount");
            return false;
        }

        if (sellOrder.getInitialAmount().compareTo(BigDecimal.ZERO) == 0){
            logger.error("trade order amount is null " + sellOrder.getId());
            System.out.println("wtf amount 2");
            return false;
        }

        /*
        if(sellOrder.getTraderOrderStatus()==TraderOrderStatus.EXECUTED){
            System.out.println("wtf executed");
            return false;
        }

        if(buyOrder.getTraderOrderStatus()==TraderOrderStatus.EXECUTED){
            System.out.println("wtf executed");
            return false;
        }*/

        if(buyOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return true;
        }

        if(sellOrder.getTradeOrderType().equals(TradeOrderType.MARKET)){
            return true;
        }

        if(buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0){
            return true;
        }

        // System.out.println("wtf end");
        return false;
    }

    private boolean checkIfExecuted(TradeOrder order){
        // System.out.println("checking if executed " + order.getAmount().setScale(8));
        if (order.getAmount().compareTo(ITradeOrderService.ZERO_VALUE)==0) {
            System.out.println("EXECUTED: " + order.getId());
            order.setTraderOrderStatus(TraderOrderStatus.EXECUTED);
            setExecutionDate(order);
            bookOrderService.remove(order);
            rabbitService.tradeOrderExecuted(tradeOrderService.convert(order));
            return true;
        } else {
            order.setTraderOrderStatus(TraderOrderStatus.IN_MARKET);
            bookOrderService.update(order);
            return false;
        }
    }

    private void setExecutionDate(TradeOrder tradeOrder){
        tradeOrder.setExecutionDate(System.currentTimeMillis());
    }

    //buy - sell
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = NotEnoughMoney.class)
    public Pair<Boolean, Boolean> executeTradeOrders(Pair<TradeOrder, TradeOrder> pair) {

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

            TradeOrder buyOrder = tradeOrderRepository.findById(pair.getLeft().getId()).orElse(null);
            TradeOrder sellOrder = tradeOrderRepository.findById(pair.getRight().getId()).orElse(null);
            System.out.println("1.2 " + " " + pair.getLeft().getId() + " " + pair.getRight().getId());
            if (sellOrder==null){
                //TODO this patch - you have a deadlock
                bookOrderService.remove(pair.getRight());
            }

            if (buyOrder==null){
                //throw new TradeException("This should not be true");
                bookOrderService.remove(pair.getLeft());
            }

            System.out.println("1.3");
            //System.out.println("1.3");

            if (!buyOrder.getDiamond().equals(sellOrder.getDiamond())) {
                throw new TradeException("Something wrong with COINS pairs.");
            }

            System.out.println("1.4");

            System.out.println("1.4.1: status b: " +  buyOrder.getTraderOrderStatus() + " " + buyOrder.getId());
            System.out.println("1.4.3: status s: " +  sellOrder.getTraderOrderStatus() + " " + sellOrder.getId());

            if (!(buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                    || buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                bookOrderService.remove(buyOrder);
                return Pair.of(false, true);
            }

            System.out.println("1.5");

            if (!(sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                    || sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                bookOrderService.remove(sellOrder);
                return Pair.of(true, false);
            }

            System.out.println("1.6");

            if (!checkIfCanExecute(pair)) {
                return Pair.of(false, false);
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
            BigDecimal orderPrice = tradeOrderService.definePrice(sellOrder, buyOrder);

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
                sellOrder = rejectTradeOrder(sellOrder);
                return Pair.of(true, false);
            }

            if (sellBalance.getAmount().compareTo(realAmount) < 0) {
                //TODO notify user
                logger.error("Not enough coins at {}" + sellBalance);
                sellOrder = rejectTradeOrder(sellOrder);
                return Pair.of(true, false);
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
                buyOrder = rejectTradeOrder(buyOrder);
                return Pair.of(false, true);
            }

            System.out.println("1.11");

            logger.debug(" BALANCE ACTIVITY TIME: " + (System.currentTimeMillis() - startActivity));

            System.out.println("1.12");

            buyOrder.setAmount(buyOrder.getAmount().subtract(realAmount));
            sellOrder.setAmount(sellOrder.getAmount().subtract(realAmount));

            buyOrder.setExecutionSum(buyOrder.getExecutionSum().add(cash));
            sellOrder.setExecutionSum(sellOrder.getExecutionSum().add(cash));

            System.out.println("1.13");
            boolean buyResult = checkIfExecuted(buyOrder);
            boolean sellResult = checkIfExecuted(sellOrder);

            System.out.println("1.14");
            buyOrder = tradeOrderRepository.save(buyOrder);
            sellOrder = tradeOrderRepository.save(sellOrder);

            rabbitService.tradeOrderUpdated(tradeOrderService.convert(buyOrder));
            rabbitService.tradeOrderUpdated(tradeOrderService.convert(sellOrder));

            System.out.println("1.15");
            long end = System.currentTimeMillis() - start;

            logger.debug("SUC EXEC TIME: " + end);

            System.out.println("exec1: "
                    + sellOrder.getId() + " "
                    + sellOrder.getTraderOrderStatus() + ", "
                    + buyOrder.getId() + ":"
                    + buyOrder.getTraderOrderStatus());
            System.out.println("exec2:  " + sellOrder.getDiamond().getName() +  " " + realAmount + " " + orderPrice);

            return Pair.of(!buyResult, !sellResult);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Pair.of(false, false);
    }

    @PreDestroy
    private void destroy(){
        service.shutdown();
    }

}
