package com.dtrade.service.core;

import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

/**
 * Created by kudelin on 7/4/17.
 */
public interface ITradeEngine {

    Pair<Boolean, Boolean> executeTradeOrders(Pair<TradeOrder, TradeOrder> pair);

    void start();

    void calculateTradeOrders();

    boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair);

    TradeOrder rejectTradeOrder(TradeOrder tradeOrder);
}
