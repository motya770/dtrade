package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.account.AccountDTO;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.service.IBalanceService;
import com.dtrade.service.IMailService;
import com.dtrade.service.ITradeOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private IMailService mailService;

    @Mock
    private IBalanceService balanceService;

    @Mock
    private ITradeOrderService tradeOrderService;

    private Account account;

    @Before
    public void setUp() {
        account = new Account("test@test.com", "encodedPwd");
        account.setId(1L);
        account.setRole(Account.F_ROLE_ACCOUNT);
        account.setEnabled(true);
        account.setConfirmed(true);
    }

    // --- find tests ---

    @Test
    public void find_existingId_returnsAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.find(1L);

        assertEquals(account, result);
    }

    // --- enable/disable tests ---

    @Test
    public void enable_setsEnabledTrue() {
        account.setEnabled(false);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        Account result = accountService.enable(1L);

        assertTrue(result.isEnabled());
    }

    @Test
    public void disable_setsEnabledFalse() {
        account.setEnabled(true);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        Account result = accountService.disable(1L);

        assertFalse(result.isEnabled());
    }

    // --- loadUserByUsername tests ---

    @Test
    public void loadUserByUsername_existingUser_returnsAccount() {
        when(accountRepository.findByMail("test@test.com")).thenReturn(account);

        UserDetails result = accountService.loadUserByUsername("test@test.com");

        assertNotNull(result);
        assertEquals("test@test.com", result.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_nonExistentUser_throwsException() {
        when(accountRepository.findByMail("unknown@test.com")).thenReturn(null);

        accountService.loadUserByUsername("unknown@test.com");
    }

    // --- confirmRegistration tests ---

    @Test
    public void confirmRegistration_validGuid_confirmsAccount() {
        account.setConfirmed(false);
        when(accountRepository.findAccountByGuidAndConfirmed("test-guid", false)).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        Account result = accountService.confirmRegistration("test-guid");

        assertTrue(result.isConfirmed());
        verify(accountRepository).save(account);
    }

    @Test(expected = TradeException.class)
    public void confirmRegistration_invalidGuid_throwsException() {
        when(accountRepository.findAccountByGuidAndConfirmed("bad-guid", false)).thenReturn(null);

        accountService.confirmRegistration("bad-guid");
    }

    // --- cancelRegistration tests ---

    @Test
    public void cancelRegistration_validGuid_cancelsAccount() {
        account.setCanceled(false);
        when(accountRepository.findAccountByGuidAndConfirmed("test-guid", false)).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        Account result = accountService.cancelRegistration("test-guid");

        assertTrue(result.isCanceled());
    }

    @Test(expected = TradeException.class)
    public void cancelRegistration_invalidGuid_throwsException() {
        when(accountRepository.findAccountByGuidAndConfirmed("bad-guid", false)).thenReturn(null);

        accountService.cancelRegistration("bad-guid");
    }

    // --- buildAccount tests ---

    @Test
    public void buildAccount_newMail_returnsAccount() {
        when(accountRepository.findByMail("new@test.com")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encoded");

        Account result = accountService.buildAccount("new@test.com", "password", "123456", "USD");

        assertNotNull(result);
        assertEquals("new@test.com", result.getMail());
        assertEquals("encoded", result.getPassword());
        assertEquals(Account.F_ROLE_ACCOUNT, result.getRole());
        assertNotNull(result.getGuid());
    }

    @Test(expected = TradeException.class)
    public void buildAccount_existingMail_throwsException() {
        when(accountRepository.findByMail("test@test.com")).thenReturn(account);

        accountService.buildAccount("test@test.com", "password", "123456", "USD");
    }

    // --- checkCurrentAccount tests ---

    @Test(expected = TradeException.class)
    public void checkCurrentAccount_nullAccount_throwsException() {
        accountService.checkCurrentAccount(null);
    }

    // --- getCurrentAccount tests ---

    @Test
    public void getCurrentAccount_noAuthentication_returnsNull() {
        SecurityContextHolder.clearContext();

        Account result = accountService.getCurrentAccount();

        assertNull(result);
    }

    @Test
    public void getCurrentAccount_nonAccountPrincipal_returnsNull() {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("stringPrincipal");

        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        Account result = accountService.getCurrentAccount();

        assertNull(result);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void getCurrentAccount_withAccountPrincipal_returnsAccount() {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(account);

        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getCurrentAccount();

        assertNotNull(result);
        assertEquals(account.getId(), result.getId());
        SecurityContextHolder.clearContext();
    }

    // --- login tests ---

    @Test
    public void login_setsSecurityContext() {
        Account result = accountService.login(account);

        assertNotNull(result);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertEquals(account, auth.getPrincipal());
        SecurityContextHolder.clearContext();
    }

    // --- save tests ---

    @Test
    public void save_delegatesToRepository() {
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.save(account);

        assertEquals(account, result);
        verify(accountRepository).save(account);
    }

    // --- findByMail tests ---

    @Test
    public void findByMail_existingMail_returnsAccount() {
        when(accountRepository.findByMail("test@test.com")).thenReturn(account);

        Account result = accountService.findByMail("test@test.com");

        assertEquals(account, result);
    }

    @Test
    public void findByMail_unknownMail_returnsNull() {
        when(accountRepository.findByMail("unknown@test.com")).thenReturn(null);

        Account result = accountService.findByMail("unknown@test.com");

        assertNull(result);
    }

    // --- getRoboAccountMail tests ---

    @Test
    public void getRoboAccountMail_formatsCorrectly() {
        Diamond d = new Diamond();
        d.setName("BTC/USD");

        String mail = accountService.getRoboAccountMail(d, 3);

        assertEquals("testAccountBTCUSD3@gmail.com", mail);
    }

    @Test
    public void getRoboAccountMail_differentRand() {
        Diamond d = new Diamond();
        d.setName("ETH/USD");

        String mail = accountService.getRoboAccountMail(d, 7);

        assertEquals("testAccountETHUSD7@gmail.com", mail);
    }

    // --- forgotPassword tests ---

    @Test(expected = TradeException.class)
    public void forgotPassword_unknownEmail_throwsException() {
        when(accountRepository.findByMail("unknown@test.com")).thenReturn(null);

        accountService.forgotPassword("unknown@test.com");
    }

    @Test
    public void forgotPassword_validEmail_setsRecoveryGuid() {
        when(accountRepository.findByMail("test@test.com")).thenReturn(account);
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        accountService.forgotPassword("test@test.com");

        assertNotNull(account.getRecoveryGuid());
        verify(mailService).sendForgotPasswordMail(account);
        verify(accountRepository).save(account);
    }

    // --- findAll tests ---

    @Test
    public void findAll_nullPage_defaultsToZero() {
        accountService.findAll(null);

        verify(accountRepository).findAll(argThat(pageable ->
                pageable.getPageNumber() == 0 && pageable.getPageSize() == 20));
    }

    // --- loginByRef tests ---

    @Test(expected = TradeException.class)
    public void loginByRef_unknownRef_throwsException() {
        when(accountRepository.findByReferral("BADREF")).thenReturn(null);

        accountService.loginByRef("BADREF");
    }
}
