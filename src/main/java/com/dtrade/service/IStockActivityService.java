package com.dtrade.service;

import com.dtrade.model.stockactivity.StockActivity;
import com.dtrade.model.tradeorder.TradeOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
public interface IStockActivityService {
    StockActivity createStockActivity(TradeOrder buyOrder, TradeOrder sellOrder,
                                      BigDecimal price, BigDecimal amount);

    @Deprecated
    StockActivity createSellStockActivity(TradeOrder tradeOrder);

    List<StockActivity> getAccountStockActivities();

}
