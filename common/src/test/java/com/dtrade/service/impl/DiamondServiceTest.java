package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondDTO;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.currency.Currency;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IScoreService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DiamondServiceTest {

    @InjectMocks
    private DiamondService diamondService;

    @Mock
    private DiamondRepository diamondRepository;

    @Mock
    private IAccountService accountService;

    @Mock
    private IScoreService scoreService;

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
        diamond.setLastRoboUpdated(System.currentTimeMillis());

        account = new Account("test@test.com", "pwd");
        account.setId(1L);
        account.setRole(Account.F_ROLE_ACCOUNT);
    }

    // --- validateDiamondCanTrade tests ---

    @Test
    public void validateDiamondCanTrade_enlistedDiamond_noException() {
        diamond.setDiamondStatus(DiamondStatus.ENLISTED);
        diamond.setLastRoboUpdated(System.currentTimeMillis());

        diamondService.validateDiamondCanTrade(diamond);
        // No exception thrown = success
    }

    @Test(expected = TradeException.class)
    public void validateDiamondCanTrade_createdStatus_throwsException() {
        diamond.setDiamondStatus(DiamondStatus.CREATED);
        diamond.setLastRoboUpdated(System.currentTimeMillis());

        diamondService.validateDiamondCanTrade(diamond);
    }

    @Test(expected = TradeException.class)
    public void validateDiamondCanTrade_hiddenStatus_throwsException() {
        diamond.setDiamondStatus(DiamondStatus.HIDDEN);
        diamond.setLastRoboUpdated(System.currentTimeMillis());

        diamondService.validateDiamondCanTrade(diamond);
    }

    @Test(expected = TradeException.class)
    public void validateDiamondCanTrade_acquiredStatus_throwsException() {
        diamond.setDiamondStatus(DiamondStatus.ACQUIRED);
        diamond.setLastRoboUpdated(System.currentTimeMillis());

        diamondService.validateDiamondCanTrade(diamond);
    }

    // --- checkDiamondOwnship tests ---

    @Test
    public void checkDiamondOwnship_sameOwner_noException() {
        diamond.setAccount(account);

        diamondService.checkDiamondOwnship(account, diamond);
        // No exception thrown = success
    }

    @Test(expected = TradeException.class)
    public void checkDiamondOwnship_differentOwner_throwsException() {
        Account otherAccount = new Account("other@test.com", "pwd");
        otherAccount.setId(2L);
        diamond.setAccount(otherAccount);

        diamondService.checkDiamondOwnship(account, diamond);
    }

    // --- getAllAvailableDTO tests ---

    @Test
    public void getAllAvailableDTO_nullDiamonds_returnsNull() {
        when(diamondRepository.getAllAvailableByName("")).thenReturn(null);

        List<DiamondDTO> result = diamondService.getAllAvailableDTO("");

        assertNull(result);
    }

    @Test
    public void getAllAvailableDTO_withDiamonds_returnsDTO() {
        Diamond d1 = new Diamond();
        d1.setId(1L);
        d1.setName("BTC/USD");
        d1.setCurrency(Currency.BTC);
        d1.setBaseCurrency(Currency.USD);
        d1.setDiamondStatus(DiamondStatus.ENLISTED);

        Diamond d2 = new Diamond();
        d2.setId(2L);
        d2.setName("ETH/USD");
        d2.setCurrency(Currency.ETH);
        d2.setBaseCurrency(Currency.USD);
        d2.setDiamondStatus(DiamondStatus.ENLISTED);

        when(diamondRepository.getAllAvailableByName("")).thenReturn(Arrays.asList(d1, d2));

        List<DiamondDTO> result = diamondService.getAllAvailableDTO("");

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    // --- getAllAvailable tests ---

    @Test
    public void getAllAvailable_nullName_usesEmptyString() {
        when(diamondRepository.getAllAvailableByName("")).thenReturn(Collections.emptyList());

        List<Diamond> result = diamondService.getAllAvailable(null);

        assertNotNull(result);
        verify(diamondRepository).getAllAvailableByName("");
    }

    @Test
    public void getAllAvailable_emptyName_usesEmptyString() {
        when(diamondRepository.getAllAvailableByName("")).thenReturn(Collections.emptyList());

        List<Diamond> result = diamondService.getAllAvailable("");

        assertNotNull(result);
        verify(diamondRepository).getAllAvailableByName("");
    }

    @Test
    public void getAllAvailable_withName_usesName() {
        when(diamondRepository.getAllAvailableByName("BTC")).thenReturn(Collections.singletonList(diamond));

        List<Diamond> result = diamondService.getAllAvailable("BTC");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(diamondRepository).getAllAvailableByName("BTC");
    }

    // --- getDiamondByCurrency tests ---

    @Test
    public void getDiamondByCurrency_returnsMatchingDiamond() {
        when(diamondRepository.findByCurrencyAndDiamondStatus(Currency.BTC, DiamondStatus.ENLISTED))
                .thenReturn(diamond);

        Diamond result = diamondService.getDiamondByCurrency(Currency.BTC);

        assertEquals(diamond, result);
    }

    // --- unsecuredUpdate tests ---

    @Test
    public void unsecuredUpdate_savesDiamond() {
        when(diamondRepository.save(diamond)).thenReturn(diamond);

        Diamond result = diamondService.unsecuredUpdate(diamond);

        assertEquals(diamond, result);
        verify(diamondRepository).save(diamond);
    }

    // --- getDiamondByName tests ---

    @Test
    public void getDiamondByName_returnsMatchingDiamond() {
        when(diamondRepository.findByName("BTC/USD")).thenReturn(diamond);

        Diamond result = diamondService.getDiamondByName("BTC/USD");

        assertEquals(diamond, result);
    }

    @Test
    public void getDiamondByName_notFound_returnsNull() {
        when(diamondRepository.findByName("UNKNOWN")).thenReturn(null);

        Diamond result = diamondService.getDiamondByName("UNKNOWN");

        assertNull(result);
    }
}
