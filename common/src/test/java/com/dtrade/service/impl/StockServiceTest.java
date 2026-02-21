package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.stock.StockRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.ITradeOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private IDiamondService diamondService;

    @Mock
    private IAccountService accountService;

    @Mock
    private ITradeOrderService tradeOrderService;

    private Account account;
    private Diamond diamond;

    @Before
    public void setUp() {
        account = new Account("test@test.com", "pwd");
        account.setId(1L);
        account.setRole(Account.F_ROLE_ACCOUNT);

        diamond = new Diamond();
        diamond.setId(1L);
        diamond.setDiamondStatus(DiamondStatus.ENLISTED);
    }

    // --- fieldsNotEmpty tests ---

    @Test
    public void fieldsNotEmpty_allFieldsPresent_returnsTrue() {
        Stock stock = new Stock();
        stock.setDiamond(diamond);
        stock.setAccount(account);
        stock.setAmount(new BigDecimal("100"));

        assertTrue(stockService.fieldsNotEmpty(stock));
    }

    @Test
    public void fieldsNotEmpty_nullDiamond_returnsFalse() {
        Stock stock = new Stock();
        stock.setDiamond(null);
        stock.setAccount(account);
        stock.setAmount(new BigDecimal("100"));

        assertFalse(stockService.fieldsNotEmpty(stock));
    }

    @Test
    public void fieldsNotEmpty_nullAccount_returnsFalse() {
        Stock stock = new Stock();
        stock.setDiamond(diamond);
        stock.setAccount(null);
        stock.setAmount(new BigDecimal("100"));

        assertFalse(stockService.fieldsNotEmpty(stock));
    }

    @Test
    public void fieldsNotEmpty_nullAmount_returnsFalse() {
        Stock stock = new Stock();
        stock.setDiamond(diamond);
        stock.setAccount(account);
        stock.setAmount(null);

        assertFalse(stockService.fieldsNotEmpty(stock));
    }

    // --- updateStockInTrade tests ---

    @Test
    public void updateStockInTrade_sellOrder_updatesStock() {
        TradeOrder order = new TradeOrder();
        order.setTradeOrderDirection(TradeOrderDirection.SELL);

        Stock stock = new Stock();
        stock.setId(1L);
        stock.setStockInTrade(new BigDecimal("50"));
        stock.setAmount(new BigDecimal("1000"));

        when(stockRepository.getSpecificStock(account, diamond)).thenReturn(stock);

        Stock result = stockService.updateStockInTrade(order, account, diamond, new BigDecimal("25"));

        assertNotNull(result);
        assertEquals(new BigDecimal("75"), result.getStockInTrade());
        verify(stockRepository).save(stock);
    }

    @Test
    public void updateStockInTrade_buyOrder_returnsNull() {
        TradeOrder order = new TradeOrder();
        order.setTradeOrderDirection(TradeOrderDirection.BUY);

        Stock result = stockService.updateStockInTrade(order, account, diamond, new BigDecimal("25"));

        assertNull(result);
    }

    @Test(expected = TradeException.class)
    public void updateStockInTrade_sellOrder_noStock_throwsException() {
        TradeOrder order = new TradeOrder();
        order.setTradeOrderDirection(TradeOrderDirection.SELL);

        when(stockRepository.getSpecificStock(account, diamond)).thenReturn(null);

        stockService.updateStockInTrade(order, account, diamond, new BigDecimal("25"));
    }

    // --- getSpecificStock tests ---

    @Test
    public void getSpecificStock_existingStock_returnsStock() {
        Stock existing = new Stock();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("500"));

        when(stockRepository.getSpecificStock(account, diamond)).thenReturn(existing);

        Stock result = stockService.getSpecificStock(account, diamond);

        assertEquals(existing, result);
    }

    @Test
    public void getSpecificStock_noStock_createsNew() {
        when(stockRepository.getSpecificStock(account, diamond)).thenReturn(null);
        when(stockRepository.save(any(Stock.class))).thenAnswer(inv -> inv.getArgument(0));

        Stock result = stockService.getSpecificStock(account, diamond);

        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getAmount());
        assertEquals(diamond, result.getDiamond());
        assertEquals(account, result.getAccount());
        assertEquals(BigDecimal.ZERO, result.getStockInTrade());
        verify(stockRepository).save(any(Stock.class));
    }

    // --- updateRoboStockAmount tests ---

    @Test(expected = TradeException.class)
    public void updateRoboStockAmount_notRoboAccount_throwsException() {
        account.setRoboAccount(false);

        stockService.updateRoboStockAmount(diamond, account);
    }

    @Test
    public void updateRoboStockAmount_roboAccount_lowStock_updates() {
        account.setRoboAccount(true);

        Stock stock = new Stock();
        stock.setId(1L);
        stock.setAmount(new BigDecimal("5000"));
        stock.setStockInTrade(new BigDecimal("4995"));

        when(stockRepository.getSpecificStock(account, diamond)).thenReturn(stock);

        Stock result = stockService.updateRoboStockAmount(diamond, account);

        assertNotNull(result);
        assertEquals(new BigDecimal("10000"), result.getAmount());
        verify(stockRepository).save(stock);
    }

    // --- makeIPO tests ---

    @Test(expected = TradeException.class)
    public void makeIPO_nullTotalStockAmount_throwsException() {
        Diamond d = new Diamond();
        d.setId(1L);
        d.setTotalStockAmount(null);
        d.setDiamondStatus(DiamondStatus.CREATED);

        when(diamondService.find(1L)).thenReturn(d);
        when(accountService.getStrictlyLoggedAccount()).thenReturn(account);
        doNothing().when(diamondService).checkDiamondOwnship(account, d);

        stockService.makeIPO(1L);
    }

    @Test(expected = TradeException.class)
    public void makeIPO_alreadyEnlisted_throwsException() {
        Diamond d = new Diamond();
        d.setId(1L);
        d.setTotalStockAmount(new BigDecimal("10000"));
        d.setDiamondStatus(DiamondStatus.ENLISTED);

        when(diamondService.find(1L)).thenReturn(d);
        when(accountService.getStrictlyLoggedAccount()).thenReturn(account);
        doNothing().when(diamondService).checkDiamondOwnship(account, d);

        stockService.makeIPO(1L);
    }
}
