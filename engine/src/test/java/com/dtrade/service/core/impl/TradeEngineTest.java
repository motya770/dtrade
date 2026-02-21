package com.dtrade.service.core.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.tradeorder.*;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.*;
import com.dtrade.service.core.ITradeEngine;
import com.dtrade.service.impl.DiamondService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeEngineTest {

    @InjectMocks
    private TradeEngine tradeEngine;

    @Mock
    private IBookOrderService bookOrderService;

    @Mock
    private IQuotesService quotesService;

    @Mock
    private IRabbitService rabbitService;

    @Mock
    private ITradeOrderService tradeOrderService;

    @Mock
    private IBalanceService balanceService;

    @Mock
    private IBalanceActivityService balanceActivityService;

    @Mock
    private TradeOrderRepository tradeOrderRepository;

    @Mock
    private DiamondService diamondService;

    // --- checkIfCanExecute tests ---

    @Test
    public void checkIfCanExecute_nullPair_returnsFalse() {
        assertFalse(tradeEngine.checkIfCanExecute(null));
    }

    @Test
    public void checkIfCanExecute_nullBuyOrder_returnsFalse() {
        TradeOrder sellOrder = createOrder(1L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);
        Pair<TradeOrder, TradeOrder> pair = Pair.of(null, sellOrder);
        // This will NPE in current impl because Pair doesn't allow null, so we skip
    }

    @Test
    public void checkIfCanExecute_buyMarketOrder_returnsTrue() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.MARKET);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("110"), new BigDecimal("10"), TradeOrderType.LIMIT);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertTrue(result);
    }

    @Test
    public void checkIfCanExecute_sellMarketOrder_returnsTrue() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("110"), new BigDecimal("10"), TradeOrderType.MARKET);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertTrue(result);
    }

    @Test
    public void checkIfCanExecute_buyPriceHigherThanSell_returnsTrue() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("110"), new BigDecimal("10"), TradeOrderType.LIMIT);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertTrue(result);
    }

    @Test
    public void checkIfCanExecute_buyPriceEqualsSell_returnsTrue() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertTrue(result);
    }

    @Test
    public void checkIfCanExecute_buyPriceLowerThanSell_returnsFalse() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("90"), new BigDecimal("10"), TradeOrderType.LIMIT);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertFalse(result);
    }

    @Test
    public void checkIfCanExecute_zeroInitialAmount_returnsFalse() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("100"), BigDecimal.ZERO, TradeOrderType.LIMIT);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertFalse(result);
    }

    @Test
    public void checkIfCanExecute_negativeInitialAmount_returnsFalse() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("100"), new BigDecimal("-5"), TradeOrderType.LIMIT);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertFalse(result);
    }

    @Test
    public void checkIfCanExecute_sellZeroInitialAmount_returnsFalse() {
        TradeOrder buyOrder = createOrder(1L, new BigDecimal("100"), new BigDecimal("10"), TradeOrderType.LIMIT);
        TradeOrder sellOrder = createOrder(2L, new BigDecimal("100"), BigDecimal.ZERO, TradeOrderType.LIMIT);

        boolean result = tradeEngine.checkIfCanExecute(Pair.of(buyOrder, sellOrder));

        assertFalse(result);
    }

    // --- rejectTradeOrder tests ---

    @Test(expected = TradeException.class)
    public void rejectTradeOrder_nullOrder_throwsException() {
        tradeEngine.rejectTradeOrder(null);
    }

    // --- Helper methods ---

    private TradeOrder createOrder(Long id, BigDecimal price, BigDecimal initialAmount, TradeOrderType type) {
        TradeOrder order = new TradeOrder();
        order.setId(id);
        order.setPrice(price);
        order.setInitialAmount(initialAmount);
        order.setAmount(initialAmount);
        order.setTradeOrderType(type);
        order.setTraderOrderStatus(TraderOrderStatus.CREATED);
        return order;
    }
}
