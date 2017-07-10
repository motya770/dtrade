package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
public interface ITradeOrderService {

    List<TradeOrder> getLiveTradeOrders();

    TradeOrder createTradeOrder(Stock stock, BigDecimal price);

    TradeOrder cancelTradeOrder(TradeOrder tradeOrder);

    TradeOrder executeTradeOrder(TradeOrder tradeOrder, Account tradeParticipant);
}
