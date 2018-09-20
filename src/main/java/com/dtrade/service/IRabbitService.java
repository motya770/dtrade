package com.dtrade.service;

import com.dtrade.model.quote.Quote;
import com.dtrade.model.tradeorder.TradeOrderDTO;

public interface IRabbitService {

    void tradeOrderCreated(TradeOrderDTO tradeOrder);

    void tradeOrderUpdated(TradeOrderDTO tradeOrder);

    void tradeOrderExecuted(TradeOrderDTO tradeOrder);

    void quoteCreated(Quote quote);
}
