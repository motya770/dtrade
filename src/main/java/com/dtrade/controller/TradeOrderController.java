package com.dtrade.controller;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kudelin on 7/15/17.
 */
@RestController
@RequestMapping(value = "/trade-order", method = RequestMethod.POST)
public class TradeOrderController {

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private IBookOrderService bookOrderService;

    @RequestMapping(value = "/book-order")
    public BookOrder getBookOrder(@RequestParam Long diamondId){

        Diamond diamond = new Diamond();
        diamond.setId(diamondId);
        return bookOrderService.getBookOrder(diamond);
    }

    @RequestMapping(value = "/create")
    public TradeOrder create(@RequestBody TradeOrder tradeOrder){
        return tradeOrderService.createTradeOrder(tradeOrder);
    }

    @RequestMapping(value = "/live-orders")
    public List<TradeOrder> getLiveOrdersByAccount(){
        return tradeOrderService.getLiveTradeOrdersByAccount();
    }


    @RequestMapping(value = "/history-orders")
    public List<TradeOrder> getHistoryTradeOrders(){
        return tradeOrderService.getHistoryTradeOrdersByAccount();
    }


    @RequestMapping(value = "/cancel")
    public TradeOrder cancel(@RequestBody TradeOrder tradeOrder){
        return tradeOrderService.cancelTradeOrder(tradeOrder);
    }
}
