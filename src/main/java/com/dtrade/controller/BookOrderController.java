package com.dtrade.controller;


import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.service.IBookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
                return bookOrderService.getBookOrderView(diamondId);
            }
        );
    }
}
