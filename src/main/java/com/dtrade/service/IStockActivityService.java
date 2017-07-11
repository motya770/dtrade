package com.dtrade.service;

import com.dtrade.model.stockactivity.StockActivity;
import com.dtrade.model.tradeorder.TradeOrder;

import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
public interface IStockActivityService {
    StockActivity createStockActivity(TradeOrder buyOrder, TradeOrder sellOrder,
                                      BigDecimal price, BigDecimal amount);
}
