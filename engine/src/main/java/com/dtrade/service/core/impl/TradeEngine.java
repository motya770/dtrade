package com.dtrade.service.core.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.*;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.*;
import com.dtrade.service.core.ITradeEngine;
import com.dtrade.service.impl.DiamondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(20);

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }


    @Autowired
    private DiamondService diamondService;

    @Override
    public void prepareAndLaunch() {

        Runnable runnable = () -> {

            diamondService.getAvailable().parallelStream().forEach(diamond -> {
                tradeOrderService.getLiveTradeOrdersByDiamond(diamond).forEach(tradeOrder -> bookOrderService.addNew(tradeOrder, true));
            });

            logger.info("Starting trade engine");
            launch();
        };

        Thread thread = new Thread(runnable);
        thread.start();

    }

    // @EventListener(ContextRefreshedEvent.class)
    @Override
    public void launch(){

       service = Executors.newScheduledThreadPool(50);
       service.scheduleWithFixedDelay(()->{
           try{
               // logger.info("IN THE LOOP!!!");
               calculateTradeOrders();
           }catch (Exception e){
               logger.error("{}", e);
           }

       }, 60_000,  40, TimeUnit.MILLISECONDS);
    }

    //private Executor ex = MoreExecutors.newSequentialExecutor(Executors.newFixedThreadPool(20));


    @Override
    public void execute(TradeOrder tradeOrder) {

        try {

            Pair<TradeOrder, TradeOrder> pair = bookOrderService.findClosest(tradeOrder.getDiamond().getId());
            boolean canExecute = false;
            Pair<TradeOrder, TradeOrder> buySell = null;
            if (pair == null || pair.getFirst() == null || pair.getSecond() == null) {
                return;
            }

            if (tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {
                buySell = Pair.of(tradeOrder, pair.getSecond());
                canExecute = checkIfCanExecute(buySell);
            } else if (tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)) {
                buySell = Pair.of(pair.getFirst(), tradeOrder);
                canExecute = checkIfCanExecute(buySell);
            }

            final Pair<TradeOrder, TradeOrder> buySellPair = buySell;
            if (canExecute) {
                Runnable quoteRunnable = () -> quotesService.issueQuote(buySellPair);
                executor.execute(quoteRunnable);

                logger.info("CAN EXECUTE " + tradeOrder.getId());

                transactionTemplate.execute(status -> {
                    return executeTradeOrders(buySellPair);
                });
            }
        }catch (Exception e){
            logger.error("{}", e);
        }
    }

    @Transactional
    @Override
    public void calculateTradeOrders(){

        //logger.debug("CALCULATING TRADE ORDERS");

        bookOrderService.getBookOrders().entrySet().parallelStream().forEach((entry)->{

            //logger.info("NEW THREAD");

            long start1 = System.currentTimeMillis();
            int exitCounter = 0;

            final List<Pair<TradeOrder, TradeOrder>> futureQuotes = new ArrayList<>();
            final List<Pair<TradeOrder, TradeOrder>> futureTrades = new ArrayList<>();

            while (true) {
                if(exitCounter>=7){
                    break;
                }
                exitCounter++;

               long start = System.currentTimeMillis();

                List<Pair<TradeOrder, TradeOrder>> buySells = bookOrderService.findClosestList(entry.getKey());
                for(Pair<TradeOrder, TradeOrder> buySell: buySells){
                    if(checkIfCanExecute(buySell)){
                        buySell.getFirst().setTradeEngineState(TradeEngineState.IN_PROGRESS);
                        buySell.getSecond().setTradeEngineState(TradeEngineState.IN_PROGRESS);
                        futureTrades.add(buySell);
                        logger.info("CAN EXECUTE " + entry.getKey());
                    }else {
                        break;
                    }
                    logger.debug("inside " + exitCounter);
                }

                /*
                if(buySell.getFirst().getTradeEngineState()!=null || buySell.getSecond().getTradeEngineState()!=null){
                    logger.info("continue: " + buySell.getFirst().getDiamond().getName() + " " + buySell.getFirst().getPrice());
                    continue;
                }*/
                //System.out.println("t1: " + (System.currentTimeMillis() - start));

            }

            if(futureTrades.size()>0) {
                Runnable tradeRunnable = () -> {
                    logger.debug("DEALS: " + futureTrades.size());
                    for (Pair<TradeOrder, TradeOrder> buySell : futureTrades) {
                        transactionTemplate.execute(status -> {

                            executeTradeOrders(buySell);
                            quotesService.issueQuote(buySell);

                            return null;
                        });
                    }
                };
                executor.execute(tradeRunnable);
            }

            /*
            if(futureQuotes.size()>0) {
                Runnable quoteRunnable = () -> {
                    transactionTemplate.execute(status -> {
                        for (Pair<TradeOrder, TradeOrder> buySell : futureQuotes) {
                            return quotesService.issueQuote(buySell);
                        }
                        return null;
                    });
                };
                executor.execute(quoteRunnable);
            }*/
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

        TradeOrder buyOrder = pair.getFirst();
        TradeOrder sellOrder = pair.getSecond();

        if(buyOrder==null || sellOrder == null){
            logger.info("wtf null");
            return false;
        }

        //buyOrder = tradeOrderRepository.findById(buyOrder.getId()).orElse(null);
        //sellOrder = tradeOrderRepository.findById(sellOrder.getId()).orElse(null);

//        if(buyOrder==null || sellOrder == null){
//            logger.info("wtf null 2");
//            return false;
//        }

        if (buyOrder.getInitialAmount().compareTo(BigDecimal.ZERO) <= 0){
            logger.error("trade order amount is null " + buyOrder.getId());
            logger.info("wtf amount");
            return false;
        }

        if (sellOrder.getInitialAmount().compareTo(BigDecimal.ZERO) <= 0){
            logger.error("trade order amount is null " + sellOrder.getId());
            logger.info("wtf amount 2");
            return false;
        }

        /*
        if(sellOrder.getTraderOrderStatus()==TraderOrderStatus.EXECUTED){
            logger.info("wtf executed");
            return false;
        }

        if(buyOrder.getTraderOrderStatus()==TraderOrderStatus.EXECUTED){
            logger.info("wtf executed");
            return false;
        }*/

        if(buyOrder.getTradeOrderType()==(TradeOrderType.MARKET)){
            return true;
        }

        if(sellOrder.getTradeOrderType()==(TradeOrderType.MARKET)){
            return true;
        }

        if(buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0){
            return true;
        }

        //logger.info("can't execute");
        return false;
    }

    private boolean checkIfExecuted(TradeOrder order){
        // logger.info("checking if executed " + order.getAmount().setScale(8));
        if (order.getAmount().compareTo(ITradeOrderService.ZERO_VALUE)==0) {
            logger.info("EXECUTED: " + order.getId());
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

        long start = System.currentTimeMillis();
        try {
                    /*
                        1) Simple example of market order should be produced
                        1a) We assume that on market orders exists
                        2) Take sellers(!) stock and transfer to buyer
                        3) Transfer money
                        4) Write all activities
                        5) work only during one minute
                     */

            logger.info("1.1");


            TradeOrder buyOrder = tradeOrderRepository.findById(pair.getFirst().getId()).orElse(null);
            TradeOrder sellOrder = tradeOrderRepository.findById(pair.getSecond().getId()).orElse(null);
            logger.info("1.2 " + " " + pair.getFirst().getId() + " " + pair.getSecond().getId());
            if (sellOrder==null){
                //TODO this patch - you have a deadlock
                bookOrderService.remove(pair.getSecond());
            }

            if (buyOrder==null){
                //throw new TradeException("This should not be true");
                bookOrderService.remove(pair.getFirst());
            }

            logger.info("1.3");
            //logger.info("1.3");

            if (!buyOrder.getDiamond().equals(sellOrder.getDiamond())) {
                throw new TradeException("Something wrong with COINS pairs.");
            }

            logger.info("1.4");

            logger.info("1.4.1: status b: " +  buyOrder.getTraderOrderStatus() + " " + buyOrder.getId());
            logger.info("1.4.3: status s: " +  sellOrder.getTraderOrderStatus() + " " + sellOrder.getId());

            if (!(buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                    || buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                bookOrderService.remove(buyOrder);
                return Pair.of(false, true);
            }

            logger.info("1.5" +  (System.currentTimeMillis() - start));

            if (!(sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)
                    || sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED))) {
                bookOrderService.remove(sellOrder);
                return Pair.of(true, false);
            }

            logger.info("1.6" );

            if (!checkIfCanExecute(pair)) {
                return Pair.of(false, false);
            }

            logger.info("1.7");

            Account buyAccount = buyOrder.getAccount();
            Account sellAccount = sellOrder.getAccount();

            //Decided that buyer and seller can be the same
//                    if (buyAccount.getId().equals(sellAccount.getId())) {
//                        return;
//                    }

            logger.info("1.8");

            //Decided to buy side to prevail on sell side
            BigDecimal orderPrice = tradeOrderService.definePrice(sellOrder, buyOrder);

            BigDecimal buyAmount = buyOrder.getAmount();
            BigDecimal sellAmount = sellOrder.getAmount();
            BigDecimal realAmount = buyAmount.min(sellAmount);

            Currency currency = buyOrder.getDiamond().getCurrency();
            Currency baseCurrency = buyOrder.getDiamond().getBaseCurrency();

            Balance buyBalance = balanceService.getBalance(baseCurrency, buyAccount);
            Balance sellBalance = balanceService.getBalance(currency, sellAccount);

            logger.info("1.9 " + (System.currentTimeMillis() - start));
            //seller don't have enough stocks
            if (buyBalance.getAmount().compareTo(realAmount) < 0) {
                //TODO notify user
                logger.error("Not enough coins at {} {}", buyBalance, buyAccount);
                buyOrder = rejectTradeOrder(buyOrder);
                return Pair.of(true, false);
            }

            if (sellBalance.getAmount().compareTo(realAmount) < 0) {
                //TODO notify user
                logger.error("Not enough coins at {} {}", sellBalance, sellAccount);
                sellOrder = rejectTradeOrder(sellOrder);
                return Pair.of(true, false);
            }

            logger.info("1.10 " + (System.currentTimeMillis() - start));

            BigDecimal cash = realAmount.multiply(orderPrice);

            long startActivity = System.currentTimeMillis();
            try {
                logger.info("1.10.1 " + Thread.currentThread().getName());
                balanceActivityService.createBalanceActivities(buyAccount, sellAccount, buyOrder,
                        sellOrder, realAmount, orderPrice);
            }catch (TradeException e){
                //TODO notify user
                logger.error("Rejecting {} because of exception {}", buyOrder, e);
                buyOrder = rejectTradeOrder(buyOrder);
                return Pair.of(false, true);
            }

            logger.info("1.11 " + + (System.currentTimeMillis() - start));

            logger.debug(" BALANCE ACTIVITY TIME: " + (System.currentTimeMillis() - startActivity));

            logger.info("1.12");

            buyOrder.setAmount(buyOrder.getAmount().subtract(realAmount));
            sellOrder.setAmount(sellOrder.getAmount().subtract(realAmount));

            buyOrder.setExecutionSum(buyOrder.getExecutionSum().add(cash));
            sellOrder.setExecutionSum(sellOrder.getExecutionSum().add(cash));

            logger.info("1.13 " + (System.currentTimeMillis() - start));
            boolean buyResult = checkIfExecuted(buyOrder);
            boolean sellResult = checkIfExecuted(sellOrder);

            logger.info("1.14");
            buyOrder = tradeOrderRepository.save(buyOrder);
            sellOrder = tradeOrderRepository.save(sellOrder);

            rabbitService.tradeOrderUpdated(tradeOrderService.convert(buyOrder));
            rabbitService.tradeOrderUpdated(tradeOrderService.convert(sellOrder));

            logger.info("1.15 ");
            long end = System.currentTimeMillis() - start;



            logger.info("exec1: "
                    + sellOrder.getId() + " "
                    + sellOrder.getTraderOrderStatus() + ", "
                    + buyOrder.getId() + ":"
                    + buyOrder.getTraderOrderStatus());
            logger.info("exec2:  " + sellOrder.getDiamond().getName() +  " " + realAmount + " " + orderPrice);

            logger.info("SUC EXEC TIME1 : " + end);
            return Pair.of(!buyResult, !sellResult);
        }catch (Exception e){
            logger.error("{}", e);
            e.printStackTrace();
        }
        logger.info("SUC EXEC TIME: " + (System.currentTimeMillis() - start));
        return Pair.of(false, false);
    }

    @PreDestroy
    private void destroy(){
        service.shutdown();
    }

}
