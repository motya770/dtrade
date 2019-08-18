package com.dtrade.service.core;

import com.dtrade.model.balanceactivity.BalanceActivityCreator;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

/**
 * Created by kudelin on 7/4/17.
 */
public interface ITradeEngine {

    BalanceActivityCreator executeTradeOrders(Pair<TradeOrder, TradeOrder> pair);

    void prepareAndLaunch();

    void launch();

    void calculateTradeOrders();

    void execute(TradeOrder tradeOrder);

    boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair);

    TradeOrder rejectTradeOrder(TradeOrder tradeOrder);
}
