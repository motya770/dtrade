package com.dtrade.service;

import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
public interface ITradeOrderService {

    List<TradeOrder> findAll();

    void calculateTradeOrders();

    boolean fieldsNotEmpty(TradeOrder tradeOrder);

    List<TradeOrder> getHistoryTradeOrdersByAccount();

    List<TradeOrder> getLiveTradeOrders();

    List<TradeOrder> getLiveTradeOrdersByAccount();

    TradeOrder createTradeOrder(TradeOrder tradeOrder);

    TradeOrder cancelTradeOrder(TradeOrder tradeOrder);

    //buy order // sell order
    void executeTradeOrders(Pair<TradeOrder, TradeOrder> pair);

    boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair);
}
