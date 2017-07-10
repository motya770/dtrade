package com.dtrade.service.core.impl;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrdersHolder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.ITradeOrderService;
import com.dtrade.service.core.ITradeEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kudelin on 7/4/17.
 */
@Service
public class TradeEngine implements ITradeEngine {

    @Autowired
    private IBookOrderService bookOrderService;

    @Autowired
    private ITradeOrderService tradeOrderService;

    private ScheduledExecutorService service;


    @PostConstruct
    private void init(){

       service = Executors.newScheduledThreadPool(10);
       service.scheduleWithFixedDelay(()->{

           calculateTradeOrders();

       }, 1_000, 1_000, TimeUnit.MILLISECONDS);
    }

    private void calculateTradeOrders(){

//        System.out.println("Start.Calculating...");
//
//
//        Set<Map.Entry<Diamond, Set<TradeOrder>>> pairs = map.entrySet();
//
//        pairs.forEach(pair->{
//            pair.getKey();
//            pair.getValue();
//        });
//
//
//
//
//        System.out.println("End.");

    }

    @PreDestroy
    private void destroy(){
        service.shutdown();
    }

    public static void main(String... args){

    }

}
