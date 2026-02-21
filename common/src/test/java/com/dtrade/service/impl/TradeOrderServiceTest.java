package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.tradeorder.*;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBookOrderServiceProxy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeOrderServiceTest {

    @InjectMocks
    private TradeOrderService tradeOrderService;

    @Mock
    private TradeOrderRepository tradeOrderRepository;

    @Mock
    private IAccountService accountService;

    @Mock
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @Mock
    private DiamondService diamondService;

    @Mock
    private BalanceService balanceService;

    private Diamond diamond;
    private Account account;

    @Before
    public void setUp() {
        diamond = new Diamond();
        diamond.setId(1L);
        diamond.setName("BTC/USD");
        diamond.setCurrency(Currency.BTC);
        diamond.setBaseCurrency(Currency.USD);
        diamond.setDiamondStatus(DiamondStatus.ENLISTED);

        account = new Account("test@test.com", "pwd");
        account.setId(1L);
        account.setRole(Account.F_ROLE_ACCOUNT);
        account.setEnabled(true);
    }

    // --- validateFields tests ---

    @Test(expected = TradeException.class)
    public void validateFields_nullAmount_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setAmount(null);

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_zeroAmount_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setAmount(BigDecimal.ZERO);

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_negativeAmount_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setAmount(new BigDecimal("-1"));

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_nullDiamond_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setDiamond(null);

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_nullAccount_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setAccount(null);

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_nullPrice_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setPrice(null);

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_zeroPrice_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setPrice(BigDecimal.ZERO);

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_negativePrice_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setPrice(new BigDecimal("-10"));

        tradeOrderService.validateFields(order);
    }

    @Test(expected = TradeException.class)
    public void validateFields_nullDirection_throwsException() {
        TradeOrder order = buildValidTradeOrder();
        order.setTradeOrderDirection(null);

        tradeOrderService.validateFields(order);
    }

    // --- convert tests ---

    @Test
    public void convert_mapsAllFields() {
        TradeOrder order = new TradeOrder();
        order.setId(42L);
        order.setAmount(new BigDecimal("10"));
        order.setInitialAmount(new BigDecimal("15"));
        order.setPrice(new BigDecimal("100.5"));
        order.setExecutionDate(1234567890L);
        order.setCreationDate(1234567800L);
        order.setTradeOrderDirection(TradeOrderDirection.BUY);
        order.setDiamond(diamond);

        TradeOrderDTO dto = tradeOrderService.convert(order);

        assertEquals(Long.valueOf(42L), dto.getId());
        assertEquals(new BigDecimal("10"), dto.getAmount());
        assertEquals(new BigDecimal("15"), dto.getInitialAmount());
        assertEquals(new BigDecimal("100.5"), dto.getPrice());
        assertEquals(Long.valueOf(1234567890L), dto.getExecutionDate());
        assertEquals(Long.valueOf(1234567800L), dto.getCreationDate());
        assertEquals(TradeOrderDirection.BUY, dto.getTradeOrderDirection());
        assertEquals(Long.valueOf(1L), dto.getDiamondId());
    }

    // --- getTradeOrderDTO tests ---

    @Test
    public void getTradeOrderDTO_emptyList_returnsEmpty() {
        List<TradeOrderDTO> result = tradeOrderService.getTradeOrderDTO(Collections.emptyList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getTradeOrderDTO_convertsList() {
        TradeOrder order1 = createFullTradeOrder(1L);
        TradeOrder order2 = createFullTradeOrder(2L);

        List<TradeOrder> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        List<TradeOrderDTO> dtos = tradeOrderService.getTradeOrderDTO(orders);

        assertEquals(2, dtos.size());
        assertEquals(Long.valueOf(1L), dtos.get(0).getId());
        assertEquals(Long.valueOf(2L), dtos.get(1).getId());
    }

    // --- definePrice tests ---

    @Test
    public void definePrice_buyMarketOrder_returnsSellPrice() {
        TradeOrder sellOrder = new TradeOrder();
        sellOrder.setPrice(new BigDecimal("100"));
        sellOrder.setTradeOrderType(TradeOrderType.LIMIT);

        TradeOrder buyOrder = new TradeOrder();
        buyOrder.setPrice(new BigDecimal("110"));
        buyOrder.setTradeOrderType(TradeOrderType.MARKET);

        BigDecimal price = tradeOrderService.definePrice(sellOrder, buyOrder);

        assertEquals(new BigDecimal("100"), price);
    }

    @Test
    public void definePrice_sellMarketOrder_returnsBuyPrice() {
        TradeOrder sellOrder = new TradeOrder();
        sellOrder.setPrice(new BigDecimal("100"));
        sellOrder.setTradeOrderType(TradeOrderType.MARKET);

        TradeOrder buyOrder = new TradeOrder();
        buyOrder.setPrice(new BigDecimal("110"));
        buyOrder.setTradeOrderType(TradeOrderType.LIMIT);

        BigDecimal price = tradeOrderService.definePrice(sellOrder, buyOrder);

        assertEquals(new BigDecimal("110"), price);
    }

    @Test
    public void definePrice_bothLimitOrders_returnsBuyPrice() {
        TradeOrder sellOrder = new TradeOrder();
        sellOrder.setPrice(new BigDecimal("100"));
        sellOrder.setTradeOrderType(TradeOrderType.LIMIT);

        TradeOrder buyOrder = new TradeOrder();
        buyOrder.setPrice(new BigDecimal("105"));
        buyOrder.setTradeOrderType(TradeOrderType.LIMIT);

        BigDecimal price = tradeOrderService.definePrice(sellOrder, buyOrder);

        assertEquals(new BigDecimal("105"), price);
    }

    // --- rereadTradeOrders tests ---

    @Test
    public void rereadTradeOrders_emptyArray_returnsNull() {
        List<TradeOrder> result = tradeOrderService.rereadTradeOrders(new Long[]{});

        assertNull(result);
    }

    // --- Helper methods ---

    private TradeOrder buildValidTradeOrder() {
        TradeOrder order = new TradeOrder();
        order.setAmount(new BigDecimal("10"));
        order.setPrice(new BigDecimal("100"));
        order.setDiamond(diamond);
        order.setAccount(account);
        order.setTradeOrderDirection(TradeOrderDirection.BUY);
        order.setTradeOrderType(TradeOrderType.LIMIT);
        return order;
    }

    private TradeOrder createFullTradeOrder(Long id) {
        TradeOrder order = new TradeOrder();
        order.setId(id);
        order.setAmount(new BigDecimal("10"));
        order.setInitialAmount(new BigDecimal("10"));
        order.setPrice(new BigDecimal("100"));
        order.setCreationDate(System.currentTimeMillis());
        order.setTradeOrderDirection(TradeOrderDirection.BUY);
        order.setDiamond(diamond);
        return order;
    }
}
