package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.stockactivity.StockActivity;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.stockactivity.StockActivityRepository;
import com.dtrade.service.IStockActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
@Service
public class StockActivityService implements IStockActivityService {


    @Autowired
    private StockActivityRepository stockActivityRepository;

    @Override
    public StockActivity createStockActivity(TradeOrder buyOrder, TradeOrder sellOrder,
                                             BigDecimal price, BigDecimal amount) {

        StockActivity stockActivity = new StockActivity();
        stockActivity.setDiamond(buyOrder.getDiamond());
        stockActivity.setBuyOrder(buyOrder);
        stockActivity.setSellOrder(sellOrder);
        stockActivity.setPrice(price);
        stockActivity.setAmount(amount);
        //stockActivity.setTotalAmount();
        stockActivity.setCreateDate(System.currentTimeMillis());

        return stockActivityRepository.save(stockActivity);
    }
}
