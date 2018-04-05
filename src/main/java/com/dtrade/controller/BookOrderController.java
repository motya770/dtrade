package com.dtrade.controller;


import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/book-order", method = RequestMethod.POST)
public class BookOrderController {

    @Autowired
    private IBookOrderService bookOrderService;

    @RequestMapping(value = "/")
    public BookOrderView getBookOrder(@RequestBody Diamond diamond){

        BookOrder bookOrder = bookOrderService.getBookOrder(diamond);
        List<TradeOrder> buyOrders  = null;
        List<TradeOrder> sellOrders  = null;

        if(bookOrder==null){
            return null;
        }
        if(bookOrder.getBuyOrders()!=null) {
            buyOrders = bookOrder.getBuyOrders().stream().limit(7).collect(Collectors.toList());
        }

        if(bookOrder.getSellOrders()!=null) {
            sellOrders = bookOrder.getSellOrders().stream().limit(7).collect(Collectors.toList());
        }
        return new BookOrderView(buyOrders, sellOrders);

    }
}
