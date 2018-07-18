package com.dtrade.service.simulators;


import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IQuotesService;
import com.dtrade.service.ITradeOrderService;
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
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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

            Runnable r1 = getRunnable("motya770@gmail.com");
            Runnable r2 = getRunnable("test@test.com");

            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6);
            executorService.scheduleAtFixedRate(r1, 100, 3_000, TimeUnit.MILLISECONDS);
            executorService.scheduleAtFixedRate(r2, 100, 3_000, TimeUnit.MILLISECONDS);

         }else{
             logger.info("Simulation is disabled");
         }
    }

    private Runnable getRunnable(String userName){
        Runnable r1 = ()->{
            logger.debug("First account simulation");
            try{
                startTrade(userName);
                Thread.currentThread().sleep(1_000);}
            catch (Exception e){
                logger.error("{}", e);
            }
        };
        return r1;
    }

    private void startTrade(String accountName){
        login(accountName);
        Account account = accountService.getStrictlyLoggedAccount();
        if(account.getBalance().compareTo(new BigDecimal("0.0"))==-1) {
            accountService.updateBalance(account, new BigDecimal("10000"));
        }
        createTradeOrderSimulated();
    }

    private void createTradeOrderSimulated() {
        diamondService.getAllAvailable("").forEach(diamond -> {
            Random rand = new Random();
            int random = rand.nextInt(2);

            //logger.info("rand value " + random);
            //random buy and random sell (simulation!! :-))
            TradeOrderDirection tradeOrderDirection = (random == 0) ? TradeOrderDirection.BUY : TradeOrderDirection.SELL;

            String[] prices = {"0.96", "0.97", "0.98", "0.99", "1.0", "1.1", "1.2", "1.3"};

        /*
        int randPrice = rand.nextInt(100);
        BigDecimal price = new BigDecimal(randPrice);
        if(price==null || price.compareTo(TradeOrderService.ZERO_VALUE)<=0){
            price = new BigDecimal("99");
        }*/


            int randAmount = rand.nextInt(10) + 1;
            int randPrice  = rand.nextInt(prices.length);

            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setAmount(new BigDecimal(randAmount));
            tradeOrder.setDiamond(diamond);
            tradeOrder.setAccount(accountService.getCurrentAccount());
            tradeOrder.setPrice(new BigDecimal(prices[randPrice]).setScale(2));
            tradeOrder.setTradeOrderType(TradeOrderType.LIMIT);
            tradeOrder.setTradeOrderDirection(tradeOrderDirection);
            tradeOrderService.createTradeOrder(tradeOrder);
        });
    }

    private void login(String userName){

        UserDetails userDetails = userDetailsService.loadUserByUsername (userName);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword (),userDetails.getAuthorities ());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }
}
