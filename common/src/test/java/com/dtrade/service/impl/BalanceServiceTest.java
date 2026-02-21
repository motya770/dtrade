package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.balance.BalanceRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BalanceServiceTest {

    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private IBalanceActivityService balanceActivityService;

    @Mock
    private IAccountService accountService;

    private Account account;

    @Before
    public void setUp() {
        account = new Account("test@test.com", "pwd");
        account.setId(1L);
        account.setRole(Account.F_ROLE_ACCOUNT);
    }

    // --- getBaseCurrencies tests ---

    @Test
    public void getBaseCurrencies_returnsOnlyBaseCurrencies() {
        List<Currency> baseCurrencies = balanceService.getBaseCurrencies();

        assertNotNull(baseCurrencies);
        assertFalse(baseCurrencies.isEmpty());
        for (Currency c : baseCurrencies) {
            assertTrue(c.isBaseCurrency());
        }
    }

    @Test
    public void getBaseCurrencies_containsUSD() {
        List<Currency> baseCurrencies = balanceService.getBaseCurrencies();

        assertTrue(baseCurrencies.contains(Currency.USD));
    }

    @Test
    public void getBaseCurrencies_doesNotContainBTC() {
        List<Currency> baseCurrencies = balanceService.getBaseCurrencies();

        assertFalse(baseCurrencies.contains(Currency.BTC));
    }

    // --- getBalance tests ---

    @Test(expected = TradeException.class)
    public void getBalance_nullCurrency_throwsException() {
        balanceService.getBalance(null, account);
    }

    @Test
    public void getBalance_existingBalance_returnsBalance() {
        Balance expected = new Balance();
        expected.setId(1L);
        expected.setAmount(new BigDecimal("1000"));
        expected.setCurrency(Currency.USD);
        expected.setAccount(account);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(expected);

        Balance result = balanceService.getBalance(Currency.USD, account);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void getBalance_noBalance_createsNew() {
        when(balanceRepository.getBalance(account, Currency.BTC)).thenReturn(null);

        Balance newBalance = new Balance();
        newBalance.setId(99L);
        newBalance.setAmount(BigDecimal.ZERO);
        newBalance.setCurrency(Currency.BTC);
        when(balanceRepository.save(any(Balance.class))).thenReturn(newBalance);

        Balance result = balanceService.getBalance(Currency.BTC, account);

        assertNotNull(result);
        verify(balanceRepository).save(any(Balance.class));
    }

    // --- updateBalance(Balance) tests ---

    @Test
    public void updateBalance_savesBalance() {
        Balance balance = new Balance();
        balance.setId(1L);
        balance.setAmount(new BigDecimal("500"));

        when(balanceRepository.save(balance)).thenReturn(balance);

        Balance result = balanceService.updateBalance(balance);

        assertNotNull(result);
        verify(balanceRepository).save(balance);
    }

    // --- updateBalance(Currency, Account, BigDecimal) tests ---

    @Test
    public void updateBalance_addsCurrencyAmount() {
        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("100"));
        existing.setFrozen(BigDecimal.ZERO);
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(existing);
        when(balanceRepository.save(any(Balance.class))).thenAnswer(inv -> inv.getArgument(0));

        Balance result = balanceService.updateBalance(Currency.USD, account, new BigDecimal("50"));

        assertEquals(new BigDecimal("150"), result.getAmount());
    }

    // --- updateFrozenBalance tests ---

    @Test
    public void updateFrozenBalance_addsFrozenAmount() {
        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("1000"));
        existing.setFrozen(new BigDecimal("100"));
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(existing);
        when(balanceRepository.save(any(Balance.class))).thenAnswer(inv -> inv.getArgument(0));

        Balance result = balanceService.updateFrozenBalance(Currency.USD, account, new BigDecimal("50"));

        assertEquals(new BigDecimal("150"), result.getFrozen());
    }

    // --- freezeAmount / unfreezeAmount tests ---

    @Test
    public void freezeAmount_addsFrozen() {
        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("1000"));
        existing.setFrozen(new BigDecimal("0"));
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(existing);
        when(balanceRepository.save(any(Balance.class))).thenAnswer(inv -> inv.getArgument(0));

        Balance result = balanceService.freezeAmount(Currency.USD, account, new BigDecimal("200"));

        assertEquals(new BigDecimal("200"), result.getFrozen());
    }

    @Test
    public void unfreezeAmount_subtractsFrozen() {
        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("1000"));
        existing.setFrozen(new BigDecimal("500"));
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(existing);
        when(balanceRepository.save(any(Balance.class))).thenAnswer(inv -> inv.getArgument(0));

        Balance result = balanceService.unfreezeAmount(Currency.USD, account, new BigDecimal("200"));

        assertEquals(new BigDecimal("300"), result.getFrozen());
    }

    // --- updateOpenSum tests ---

    @Test
    public void updateOpenSum_buyOrder_updatesBaseCurrency() {
        TradeOrder order = new TradeOrder();
        order.setTradeOrderDirection(TradeOrderDirection.BUY);

        Diamond diamond = new Diamond();
        diamond.setId(1L);
        diamond.setBaseCurrency(Currency.USD);
        diamond.setCurrency(Currency.BTC);
        order.setDiamond(diamond);

        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("10000"));
        existing.setFrozen(BigDecimal.ZERO);
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(existing);
        when(balanceRepository.save(any(Balance.class))).thenAnswer(inv -> inv.getArgument(0));

        balanceService.updateOpenSum(order, account, new BigDecimal("500"), new BigDecimal("5"));

        // For BUY, updateOpen should be called with sum (500) on baseCurrency (USD)
        assertEquals(new BigDecimal("500"), existing.getOpen());
    }

    @Test
    public void updateOpenSum_sellOrder_updatesCurrency() {
        TradeOrder order = new TradeOrder();
        order.setTradeOrderDirection(TradeOrderDirection.SELL);

        Diamond diamond = new Diamond();
        diamond.setId(1L);
        diamond.setBaseCurrency(Currency.USD);
        diamond.setCurrency(Currency.BTC);
        order.setDiamond(diamond);

        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("100"));
        existing.setFrozen(BigDecimal.ZERO);
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.BTC)).thenReturn(existing);
        when(balanceRepository.save(any(Balance.class))).thenAnswer(inv -> inv.getArgument(0));

        balanceService.updateOpenSum(order, account, new BigDecimal("500"), new BigDecimal("5"));

        // For SELL, updateOpen should be called with amount (5) on currency (BTC)
        assertEquals(new BigDecimal("5"), existing.getOpen());
    }

    // --- updateRoboBalances tests ---

    @Test(expected = TradeException.class)
    public void updateRoboBalances_notRoboAccount_throwsException() {
        account.setRoboAccount(false);

        balanceService.updateRoboBalances(Currency.USD, account);
    }

    @Test
    public void updateRoboBalances_lowBalance_updates() {
        account.setRoboAccount(true);

        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("-100"));
        existing.setFrozen(BigDecimal.ZERO);
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(existing);
        when(balanceRepository.save(any(Balance.class))).thenAnswer(inv -> inv.getArgument(0));

        Balance result = balanceService.updateRoboBalances(Currency.USD, account);

        assertNotNull(result);
    }

    @Test
    public void updateRoboBalances_highBalance_returnsNull() {
        account.setRoboAccount(true);

        Balance existing = new Balance();
        existing.setId(1L);
        existing.setAmount(new BigDecimal("200000"));
        existing.setFrozen(BigDecimal.ZERO);
        existing.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(existing);

        Balance result = balanceService.updateRoboBalances(Currency.USD, account);

        assertNull(result);
    }

    // --- getOpenSum tests ---

    @Test
    public void getOpenSum_returnsOpenValue() {
        Balance balance = new Balance();
        balance.setOpen(new BigDecimal("750"));
        balance.setAmount(new BigDecimal("1000"));
        balance.setFrozen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(balance);

        BigDecimal openSum = balanceService.getOpenSum(Currency.USD, account);

        assertEquals(new BigDecimal("750"), openSum);
    }

    // --- getFrozen tests ---

    @Test
    public void getFrozen_returnsFrozenValue() {
        Balance balance = new Balance();
        balance.setFrozen(new BigDecimal("300"));
        balance.setAmount(new BigDecimal("1000"));
        balance.setOpen(BigDecimal.ZERO);

        when(balanceRepository.getBalance(account, Currency.USD)).thenReturn(balance);

        BigDecimal frozen = balanceService.getFrozen(Currency.USD, account);

        assertEquals(new BigDecimal("300"), frozen);
    }
}
