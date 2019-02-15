package com.dtrade.controller;

import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderAccountHolder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.dtrade.service.ITradeOrderService;
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

   // @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/live-orders-reread")
    public CompletableFuture<List<TradeOrder>> rereadLiveOrders(@RequestBody Long[] ids){

       return CompletableFuture.supplyAsync(() -> {
                return tradeOrderService.rereadTradeOrders(ids);
            }
        );
    }

   // @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/create")
    public TradeOrderAccountHolder create(@RequestBody TradeOrder tradeOrder){

        return tradeOrderService.createTradeOrder(tradeOrder);

//        return CompletableFuture.supplyAsync(() -> {
//
//                }
//        );
    }

   // @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/live-orders")
    public Page<TradeOrder> getLiveOrdersByAccount(@RequestParam(required = false) Integer pageNumber ){
        return tradeOrderService.getLiveTradeOrdersByAccount(pageNumber);
//        return CompletableFuture.supplyAsync(() -> {
//
//                }
//        );
    }

    //@Cacheable(value = "historyOrders", cacheManager = "concurrent")
    @RequestMapping(value = "/history-orders")
    public CompletableFuture<List<TradeOrderDTO>> getHistoryTradeOrders(@RequestBody Long diamondId)throws Exception {



        //List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamond);
       // Hibernate.initialize(tradeOrders);

        //logger.debug("TIME FOR getHistoryTradeOrders: {}", (start  - System.currentTimeMillis()));

//        List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamondId);
//        return tradeOrderService.getTradeOrderDTO(tradeOrders);

        return  CompletableFuture.supplyAsync(()-> {
            long start = System.currentTimeMillis();
            List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrdersCashed(diamondId);
            if(tradeOrders==null){
                return null;
            }
            logger.debug("!!!!!!!!!!!!!end1: " + (System.currentTimeMillis() - start) + " " + tradeOrders.size());
            List<TradeOrderDTO> result =  tradeOrderService.getTradeOrderDTO(tradeOrders);

           logger.debug("!!!!!!!!!!!!!end2: " + (System.currentTimeMillis() - start));
            return result;

        });
    }

   // @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/account-history-orders")
    public Page<TradeOrder> getHistoryTradeOrdersByAcount(@RequestParam Integer pageNumber,
                                                          @RequestParam Long startTime,
                                                          @RequestParam Long endTime){
        return tradeOrderService.getHistoryTradeOrdersByAccount(startTime, endTime, pageNumber);

//        return CompletableFuture.supplyAsync(() -> {
//
//                }
//        );
    }

    @RequestMapping(value = "/cancel")
    public TradeOrder cancel(@RequestBody TradeOrder tradeOrder){
        return tradeOrderService.cancelTradeOrder(tradeOrder);

//        return CompletableFuture.supplyAsync(() -> {
//
//                }
//        );
    }
}
