package com.dtrade.controller;

import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @RequestMapping(value = "/create")
    public TradeOrder create(@RequestBody TradeOrder tradeOrder){
        return tradeOrderService.createTradeOrder(tradeOrder);
    }

    @RequestMapping(value = "/live-orders")
    public Page<TradeOrder> getLiveOrdersByAccount(@RequestParam(required = false) Integer pageNumber ){
        return tradeOrderService.getLiveTradeOrdersByAccount(pageNumber);
    }


    @RequestMapping(value = "/history-orders")
    public List<TradeOrder> getHistoryTradeOrders(){
        return tradeOrderService.getHistoryTradeOrders();
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
