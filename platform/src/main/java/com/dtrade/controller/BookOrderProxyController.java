package com.dtrade.controller;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.service.IBookOrderServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/book-order", method = RequestMethod.POST)
public class BookOrderProxyController {

    @Autowired
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @RequestMapping(value = "/get-diamonds-spread", method = RequestMethod.POST)
    public Mono<String> getLastQuotesForDiamonds(@RequestBody(required = true) ArrayList<Long> diamonds){
        return bookOrderServiceProxy.getSpreadForDiamonds(diamonds);
    }

    @RequestMapping(value = "/")
    public Mono<BookOrderView> getBookOrder(@RequestBody Long diamondId){
        return bookOrderServiceProxy.getBookOrderView(diamondId);
    }
}
