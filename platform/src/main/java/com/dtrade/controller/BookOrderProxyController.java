package com.dtrade.controller;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.service.IBookOrderServiceProxy;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/book-order", method = RequestMethod.POST)
public class BookOrderProxyController {

    @Autowired
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @RequestMapping(value = "/get-diamonds-spread", method = RequestMethod.POST)
    public CompletableFuture<List<Pair<?, ?>>> getLastQuotesForDiamonds(@RequestBody(required = true) ArrayList<Long> diamonds){

        return CompletableFuture.supplyAsync(() -> {
                return bookOrderServiceProxy.getSpreadForDiamonds(diamonds);
            }
        );
    }

    @RequestMapping(value = "/")
    public CompletableFuture<BookOrderView> getBookOrder(@RequestBody Long diamondId){

        return CompletableFuture.supplyAsync(() -> {
                return bookOrderServiceProxy.getBookOrderView(diamondId);
            }
        );
    }
}
