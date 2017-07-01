package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;

import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
public interface ITradeOrderService {

    TradeOrder createTradeOrder(Stock stock, BigDecimal price);

    TradeOrder cancelTradeOrder(TradeOrder tradeOrder);

    TradeOrder executeTradeOrder(TradeOrder tradeOrder, Account tradeParticipant);
}
