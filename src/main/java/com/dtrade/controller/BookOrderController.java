package com.dtrade.controller;


import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/book-order", method = RequestMethod.POST)
public class BookOrderController {

    @Autowired
    private IBookOrderService bookOrderService;

   // @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/get-diamonds-spread", method = RequestMethod.POST)
    public CompletableFuture<List<Pair<?, ?>>> getLastQuotesForDiamonds(@RequestBody(required = true) ArrayList<Long> diamonds){

        return CompletableFuture.supplyAsync(() -> {
                return bookOrderService.getSpreadForDiamonds(diamonds);
            }
        );
    }

   // @Cacheable(value="A", cacheManager="timeoutCacheManager")
    @RequestMapping(value = "/")
    public CompletableFuture<BookOrderView> getBookOrder(@RequestBody Long diamondId){

        return CompletableFuture.supplyAsync(() -> {
            //long start = System.currentTimeMillis();
            BookOrder bookOrder = bookOrderService.getBookOrder(diamondId);
            List<TradeOrder> buyOrders  = null;
            List<TradeOrder> sellOrders  = null;

            if(bookOrder==null){
                return null;
            }
            if(bookOrder.getBuyOrders()!=null) {
                buyOrders = bookOrder.getBuyOrders().stream().limit(10).collect(Collectors.toList());
            }

            if(bookOrder.getSellOrders()!=null) {
                sellOrders = bookOrder.getSellOrders().stream().limit(10).collect(Collectors.toList());
                Collections.reverse(sellOrders);
            }

            //System.out.println("BookOrder: " + (System.currentTimeMillis() - start));
            return new BookOrderView(buyOrders, sellOrders);
        }
        );
    }
}
