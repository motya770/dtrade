package com.dtrade.model;

import com.dtrade.model.tradeorder.*;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TradeOrderTest {

    @Test
    public void setTraderOrderStatus_created_setsLiveIndex() {
        TradeOrder order = new TradeOrder();
        order.setTraderOrderStatus(TraderOrderStatus.CREATED);

        assertEquals(TraderOrderStatus.CREATED, order.getTraderOrderStatus());
        assertEquals(TraderOrderStatusIndex.LIVE, order.getTraderOrderStatusIndex());
    }

    @Test
    public void setTraderOrderStatus_inMarket_setsLiveIndex() {
        TradeOrder order = new TradeOrder();
        order.setTraderOrderStatus(TraderOrderStatus.IN_MARKET);

        assertEquals(TraderOrderStatus.IN_MARKET, order.getTraderOrderStatus());
        assertEquals(TraderOrderStatusIndex.LIVE, order.getTraderOrderStatusIndex());
    }

    @Test
    public void setTraderOrderStatus_executed_setsHistoryIndex() {
        TradeOrder order = new TradeOrder();
        order.setTraderOrderStatus(TraderOrderStatus.EXECUTED);

        assertEquals(TraderOrderStatus.EXECUTED, order.getTraderOrderStatus());
        assertEquals(TraderOrderStatusIndex.HISTORY, order.getTraderOrderStatusIndex());
    }

    @Test
    public void setTraderOrderStatus_canceled_setsHistoryIndex() {
        TradeOrder order = new TradeOrder();
        order.setTraderOrderStatus(TraderOrderStatus.CANCELED);

        assertEquals(TraderOrderStatus.CANCELED, order.getTraderOrderStatus());
        assertEquals(TraderOrderStatusIndex.HISTORY, order.getTraderOrderStatusIndex());
    }

    @Test
    public void setTraderOrderStatus_rejected_setsHistoryIndex() {
        TradeOrder order = new TradeOrder();
        order.setTraderOrderStatus(TraderOrderStatus.REJECTED);

        assertEquals(TraderOrderStatus.REJECTED, order.getTraderOrderStatus());
        assertEquals(TraderOrderStatusIndex.HISTORY, order.getTraderOrderStatusIndex());
    }

    @Test
    public void equals_sameId_returnsTrue() {
        TradeOrder order1 = new TradeOrder();
        order1.setId(1L);

        TradeOrder order2 = new TradeOrder();
        order2.setId(1L);

        assertEquals(order1, order2);
    }

    @Test
    public void equals_differentId_returnsFalse() {
        TradeOrder order1 = new TradeOrder();
        order1.setId(1L);

        TradeOrder order2 = new TradeOrder();
        order2.setId(2L);

        assertNotEquals(order1, order2);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        TradeOrder order = new TradeOrder();
        order.setId(1L);

        assertEquals(order, order);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        TradeOrder order = new TradeOrder();
        order.setId(1L);

        assertNotEquals(order, "not a trade order");
    }

    @Test
    public void hashCode_sameId_sameHash() {
        TradeOrder order1 = new TradeOrder();
        order1.setId(1L);

        TradeOrder order2 = new TradeOrder();
        order2.setId(1L);

        assertEquals(order1.hashCode(), order2.hashCode());
    }

    @Test
    public void toString_containsFields() {
        TradeOrder order = new TradeOrder();
        order.setId(1L);
        order.setAmount(new BigDecimal("100"));
        order.setInitialAmount(new BigDecimal("100"));
        order.setPrice(new BigDecimal("50.5"));
        order.setTradeOrderDirection(TradeOrderDirection.BUY);
        order.setTradeOrderType(TradeOrderType.MARKET);
        order.setTraderOrderStatus(TraderOrderStatus.CREATED);

        String str = order.toString();
        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("amount=100"));
        assertTrue(str.contains("BUY"));
        assertTrue(str.contains("MARKET"));
    }
}
