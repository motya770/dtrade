package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.dtrade.service.ITradeOrderService;
import com.dtrade.service.impl.QuotesService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Created by kudelin on 7/15/17.
 */
@RestController
@RequestMapping(value = "/trade-order", method = RequestMethod.POST)
public class TradeOrderController {

    @Autowired
    private ITradeOrderService tradeOrderService;

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderController.class);

    @RequestMapping(value = "/create")
    public TradeOrder create(@RequestBody TradeOrder tradeOrder){
        return tradeOrderService.createTradeOrder(tradeOrder);
    }

    @RequestMapping(value = "/live-orders")
    public Page<TradeOrder> getLiveOrdersByAccount(@RequestParam(required = false) Integer pageNumber ){
        return tradeOrderService.getLiveTradeOrdersByAccount(pageNumber);
    }

    @RequestMapping(value = "/history-orders")
    public CompletableFuture<List<TradeOrderDTO>> getHistoryTradeOrders(@RequestBody Diamond diamond)throws Exception {

        //long start = System.currentTimeMillis();

        //List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamond);
       // Hibernate.initialize(tradeOrders);

        //logger.debug("TIME FOR getHistoryTradeOrders: {}", (start  - System.currentTimeMillis()));

        return  CompletableFuture.supplyAsync(()-> {
            List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamond);
            return tradeOrderService.getTradeOrderDTO(tradeOrders);
        });
    }

    @RequestMapping(value = "/account-history-orders")
    public Page<TradeOrder> getHistoryTradeOrdersByAcount(@RequestParam Integer pageNumber){
        return tradeOrderService.getHistoryTradeOrdersByAccount(pageNumber);
    }

    @RequestMapping(value = "/cancel")
    public TradeOrder cancel(@RequestBody TradeOrder tradeOrder){
        return tradeOrderService.cancelTradeOrder(tradeOrder);
    }
}
