package com.dtrade;

import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.service.impl.AccountService;
import com.dtrade.service.impl.DiamondService;
import com.dtrade.service.impl.TradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class BaseTest {

    public static final String F_DEFAULT_TEST_ACCOUNT = "motya770@gmail.com";

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TradeOrderService tradeOrderService;

    public TradeOrder createTestTradeOrder(){
        return createTestTradeOrder(null);
    }

    public TradeOrder createTestTradeOrder(TradeOrderType tradeOrderType){
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setAmount(new BigDecimal("10.0"));
        tradeOrder.setDiamond(diamondService.getAvailable().stream().findFirst().get());
        tradeOrder.setAccount(accountService.getCurrentAccount());
        tradeOrder.setPrice(new BigDecimal("100.00"));
        if(tradeOrderType==null){
            tradeOrderType = TradeOrderType.BUY;
        }
        tradeOrder.setTradeOrderType(tradeOrderType);
        tradeOrder = tradeOrderService.createTradeOrder(tradeOrder);
        return tradeOrder;
    }

    public TradeOrder createTestBuyTradeOrder(){
        return createTestTradeOrder(TradeOrderType.BUY);
    }

    public TradeOrder createTestSellTradeOrder(){
        return createTestTradeOrder(TradeOrderType.SELL);
    }

}
