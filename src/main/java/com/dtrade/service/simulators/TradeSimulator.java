package com.dtrade.service.simulators;


import com.dtrade.service.IAccountService;
import com.dtrade.service.ITradeOrderService;
import com.dtrade.service.impl.TradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

//@Component
@Profile("local")//maybe too much?)
public class TradeSimulator {

    private static final Logger logger = LoggerFactory.getLogger(TradeSimulator.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private Environment environment;

    @PostConstruct
    private void init(){

         String simulateTrade = environment.getProperty("simulateTrade");
         if(StringUtils.isEmpty(simulateTrade)){
             logger.info("Simulation is disabled");
             return;
         }

         if(Boolean.valueOf(simulateTrade).equals(Boolean.TRUE)){

             System.out.println("starting simulation....");

             //IAccountService accountService = accountService.getCurrentAccount();

         }else{
             logger.info("Simulation is disabled");
         }

    }
}
