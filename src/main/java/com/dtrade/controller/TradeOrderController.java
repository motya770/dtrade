package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.dtrade.service.ITradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/live-orders-reread")
    public CompletableFuture<List<TradeOrder>> rereadLiveOrders(@RequestBody Long[] ids){

       return CompletableFuture.supplyAsync(() -> {
                return tradeOrderService.rereadTradeOrders(ids);
            }
        );
    }

    @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/create")
    public CompletableFuture<TradeOrder> create(@RequestBody TradeOrder tradeOrder){
        return CompletableFuture.supplyAsync(() -> {
                    return tradeOrderService.createTradeOrder(tradeOrder);
                }
        );
    }

    @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/live-orders")
    public CompletableFuture<Page<TradeOrder>> getLiveOrdersByAccount(@RequestParam(required = false) Integer pageNumber ){
        return CompletableFuture.supplyAsync(() -> {
                    return tradeOrderService.getLiveTradeOrdersByAccount(pageNumber);
                }
        );
    }

    @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/history-orders")
    public CompletableFuture<List<TradeOrderDTO>> getHistoryTradeOrders(@RequestBody Long diamondId)throws Exception {

        //long start = System.currentTimeMillis();

        //List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamond);
       // Hibernate.initialize(tradeOrders);

        //logger.debug("TIME FOR getHistoryTradeOrders: {}", (start  - System.currentTimeMillis()));

        return  CompletableFuture.supplyAsync(()-> {
            List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamondId);
            return tradeOrderService.getTradeOrderDTO(tradeOrders);
        });
    }

    @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/account-history-orders")
    public CompletableFuture<Page<TradeOrder>> getHistoryTradeOrdersByAcount(@RequestParam Integer pageNumber){
        return CompletableFuture.supplyAsync(() -> {
                    return tradeOrderService.getHistoryTradeOrdersByAccount(pageNumber);
                }
        );
    }

    @RequestMapping(value = "/cancel")
    public CompletableFuture<TradeOrder> cancel(@RequestBody TradeOrder tradeOrder){
        return CompletableFuture.supplyAsync(() -> {
                      return tradeOrderService.cancelTradeOrder(tradeOrder);
                }
        );
    }
}
