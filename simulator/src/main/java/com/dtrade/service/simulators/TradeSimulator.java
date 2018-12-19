package com.dtrade.service.simulators;


import com.dtrade.model.account.Account;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TradeSimulator {

    private static final Logger logger = LoggerFactory.getLogger(TradeSimulator.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private Environment environment;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private IBalanceService balanceService;

    @Autowired
    private IStockService stockService;


    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    //two different logins f
    @PostConstruct
    private void init(){

         String simulateTrade = environment.getProperty("trade.simulateTrade");
         if(StringUtils.isEmpty(simulateTrade)){
             logger.info("Simulation is disabled");
             return;
         }

         if(Boolean.valueOf(simulateTrade).equals(Boolean.TRUE)){
            logger.info("Starting simulation....");

            Runnable r1 = getRunnable();// "motya770@gmail.com"

            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6);
            executorService.scheduleAtFixedRate(r1, 15_000, 12_000, TimeUnit.MILLISECONDS);

            Runnable r2 = getRunnable();  // "test@test.com"
            executorService.scheduleAtFixedRate(r2, 30_000, 10_000, TimeUnit.MILLISECONDS);

         }else{
             logger.info("Simulation is disabled");
         }
    }

    private Runnable getRunnable(){
        Runnable r1 = ()->{
            logger.debug("First account simulation");
            try{
                transactionTemplate.execute((status) -> {
                    startTrade();
                    return null;
                });
                Thread.currentThread().sleep(1_000);}
            catch (Exception e){
                logger.error("{}", e);
            }
        };
        return r1;
    }

    private void startTrade(){
        //createTradeOrderSimulated();
        createMarketMakerTrades();
    }


    private void createMarketMakerTrades(){
        List<Diamond> diamonds =   diamondService.getAllAvailable("");

        diamonds.forEach(diamond -> {
            TradeOrder to =  transactionTemplate.execute(status -> {
                Random rand = new Random();
                int random = rand.nextInt(2);

                int accountRandom = rand.nextInt(IAccountService.MAX_ROBO_ACCOUNT_COUNT);

                //logger.info("rand value " + random);
                //random buy and random sell (simulation!! :-))
                TradeOrderDirection tradeOrderDirection = (random == 0) ? TradeOrderDirection.BUY : TradeOrderDirection.SELL;

                BigDecimal highEnd =  diamond.getRoboHighEnd();
                BigDecimal lowEnd = diamond.getRoboLowEnd();

                BigDecimal highEndSecond = highEnd.multiply(new BigDecimal("1.003"));
                BigDecimal lowEndFirst = lowEnd.multiply(new BigDecimal("0.997"));

                //we just want to create the market but not to buy or sell
                BigDecimal price =  (tradeOrderDirection == TradeOrderDirection.BUY) ?
                        getRandomRangePrice(lowEndFirst, lowEnd) : getRandomRangePrice(highEnd, highEndSecond);

                BigDecimal amount =getRandomAmount(diamond);

                TradeOrder tradeOrder = new TradeOrder();
                tradeOrder.setAmount(amount);
                tradeOrder.setDiamond(diamond);

                String mail = accountService.getRoboAccountMail(diamond, accountRandom);
                login(mail);

                Account account =  accountService.getStrictlyLoggedAccount();

                Arrays.stream(Currency.values()).forEach(currency ->
                        balanceService.updateRoboBalances(currency, account));

                tradeOrder.setAccount(accountService.getCurrentAccount());
                tradeOrder.setPrice(price);
                tradeOrder.setTradeOrderType(TradeOrderType.LIMIT);
                tradeOrder.setTradeOrderDirection(tradeOrderDirection);
                return tradeOrder;
            });

            transactionTemplate.execute(status -> tradeOrderService.createTradeOrder(to));
        });
    }


    private BigDecimal getRandomAmount(Diamond diamond){

       BigDecimal rangeMin =  diamond.getRoboMaxAmount().divide(new BigDecimal("10"));
       BigDecimal rangeMax = diamond.getRoboMaxAmount();

       Random r = new Random();
       //to generate a random value between rangeMin and rangeMax:

        /*
        To generate a random value between rangeMin and rangeMax:

        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        */

       BigDecimal randomAmount =
               rangeMin.add((rangeMax.min(rangeMin) ).multiply(new BigDecimal(r.nextDouble()))).setScale(8, BigDecimal.ROUND_HALF_UP);
       return randomAmount;

    }

    private BigDecimal getRandomRangePrice(BigDecimal start, BigDecimal end){
        Random rand = new Random();
        BigDecimal[] prices = new BigDecimal[8];
        BigDecimal step = end.subtract(start).divide(new BigDecimal(prices.length));

        for(int i=0; i < prices.length; i++){
            start = start.add(step);
            start = start.setScale(8, BigDecimal.ROUND_HALF_UP);
            prices[i] = start;
        }
        int priceIndex  = rand.nextInt(prices.length);
        return prices[priceIndex];
    }

    private void createTradeOrderSimulated() {

        List<Diamond> diamonds =   diamondService.getAllAvailable("");

        diamonds.forEach(diamond -> {

            Random rand = new Random();
            int random = rand.nextInt(2);

            int accountRand = rand.nextInt(IAccountService.MAX_ROBO_ACCOUNT_COUNT);

            //logger.info("rand value " + random);
            //random buy and random sell (simulation!! :-))
           TradeOrderDirection tradeOrderDirection = (random == 0) ? TradeOrderDirection.BUY : TradeOrderDirection.SELL;

           BigDecimal highEnd =  diamond.getRoboHighEnd();
           BigDecimal lowEnd = diamond.getRoboLowEnd();

           //fallback
           if(lowEnd==null){
              lowEnd = new BigDecimal( "0.96");
           }
           if(highEnd==null){
               highEnd = new BigDecimal( "1.3");
           }

           //we can optimize here TODO
           BigDecimal[] prices = new BigDecimal[8];
           BigDecimal step = highEnd.subtract(lowEnd).divide(new BigDecimal(prices.length));

           for(int i=0; i < prices.length; i++){
               lowEnd = lowEnd.add(step).setScale(8, BigDecimal.ROUND_HALF_UP);
               prices[i] = lowEnd;
           }

            int randPrice  = rand.nextInt(prices.length);

            BigDecimal amount = getRandomAmount(diamond);

            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setAmount(amount);
            tradeOrder.setDiamond(diamond);

            String mail = accountService.getRoboAccountMail(diamond, accountRand);
            login(mail);

            Account account =  accountService.getStrictlyLoggedAccount();

            Arrays.stream(Currency.values()).forEach(currency ->
                    balanceService.updateRoboBalances(currency, account));

            tradeOrder.setAccount(accountService.getCurrentAccount());
            tradeOrder.setPrice(prices[randPrice]);
            tradeOrder.setTradeOrderType(TradeOrderType.LIMIT);
            tradeOrder.setTradeOrderDirection(tradeOrderDirection);

            transactionTemplate.execute(status -> tradeOrderService.createTradeOrder(tradeOrder));
        });
    }


    private void login(String userName){

        UserDetails userDetails = userDetailsService.loadUserByUsername (userName);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword (),userDetails.getAuthorities ());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
