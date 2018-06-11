package com.dtrade.service.impl;

import com.dtrade.model.stockactivity.StockActivity;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.stockactivity.StockActivityRepository;
import com.dtrade.service.IStockActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
@Service
public class StockActivityService implements IStockActivityService {


    @Autowired
    private StockActivityRepository stockActivityRepository;

    @Override
    public StockActivity createSellStockActivity(TradeOrder tradeOrder) {

        StockActivity stockActivity = new StockActivity();
        stockActivity.setDiamond(tradeOrder.getDiamond());
        stockActivity.setBuyOrder(null);
        stockActivity.setSellOrder(tradeOrder);
        stockActivity.setPrice(tradeOrder.getPrice());
        stockActivity.setAmount(tradeOrder.getAmount());
        stockActivity.setCreateDate(System.currentTimeMillis());

        return stockActivityRepository.save(stockActivity);
    }

    @Override
    public StockActivity createStockActivity(TradeOrder buyOrder, TradeOrder sellOrder,
                                             BigDecimal price, BigDecimal amount) {

        StockActivity stockActivity = new StockActivity();
        stockActivity.setDiamond(buyOrder.getDiamond());
        stockActivity.setBuyOrder(buyOrder);
        stockActivity.setSellOrder(sellOrder);
        stockActivity.setPrice(price);
        stockActivity.setAmount(amount);
        stockActivity.setCreateDate(System.currentTimeMillis());
        return stockActivityRepository.save(stockActivity);
    }


    @Override
    public List<StockActivity> getAccountStockActivities() {
        return null;
    }
}
