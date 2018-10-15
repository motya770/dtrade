package com.dtrade;


import com.dtrade.model.stockactivity.StockActivity;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.service.IStockActivityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StockActivityService  extends BaseTest{

    @Autowired
    private IStockActivityService stockActivityService;


    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void  testCreateStockActivity(){

        TradeOrder buyOrder = createTestTradeOrder(TradeOrderDirection.BUY);
        TradeOrder sellOrder = createTestTradeOrder(TradeOrderDirection.SELL);
        BigDecimal price = new BigDecimal("10.000001");
        BigDecimal amount = new BigDecimal("0.0003");

         BigDecimal sum = price.multiply(amount);

        StockActivity stockActivity =  stockActivityService.createStockActivity(buyOrder, sellOrder, sum, amount, price);

        Assert.assertNotNull(stockActivity);
        Assert.assertEquals(buyOrder, stockActivity.getBuyOrder());
        Assert.assertEquals(sellOrder, stockActivity.getSellOrder());
        Assert.assertTrue(stockActivity.getSum().compareTo(sum)==0);
    }
}
