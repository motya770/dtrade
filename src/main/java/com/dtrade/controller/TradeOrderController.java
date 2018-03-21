package com.dtrade.controller;

import com.dtrade.controller.view.View;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.ITradeOrderService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public List<TradeOrder> getLiveOrdersByAccount(){
        return tradeOrderService.getLiveTradeOrdersByAccount();
    }


    @RequestMapping(value = "/history-orders")
    public List<TradeOrder> getHistoryTradeOrders(){
        return tradeOrderService.getHistoryTradeOrders();
    }

    @RequestMapping(value = "/account-history-orders")
    public List<TradeOrder> getHistoryTradeOrdersByAcount(){
        return tradeOrderService.getHistoryTradeOrdersByAccount();
    }

    @RequestMapping(value = "/cancel")
    public TradeOrder cancel(@RequestBody TradeOrder tradeOrder){
        return tradeOrderService.cancelTradeOrder(tradeOrder);
    }
}
