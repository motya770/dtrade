package com.dtrade.service.core.impl;

import com.dtrade.service.ITradeOrderService;
import com.dtrade.service.core.ITradeEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kudelin on 7/4/17.
 */
@Service
public class TradeEngine implements ITradeEngine {

    @Autowired
    private ITradeOrderService tradeOrderService;


    private ScheduledExecutorService service;

    @EventListener(ContextRefreshedEvent.class)
    private void init(){

       service = Executors.newScheduledThreadPool(10);
       //TODO rewrite
       service.scheduleWithFixedDelay(()->{
           try{
              // System.out.println("IN THE LOOP!!!");
               tradeOrderService.calculateTradeOrders();
           }catch (Exception e){
               e.printStackTrace();
           }

       }, 1_000, 5, TimeUnit.MILLISECONDS);
    }

    @PreDestroy
    private void destroy(){
        service.shutdown();
    }

}
