package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.account.AccountDTO;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IMailService;
import com.dtrade.service.ITradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class AccountService implements IAccountService, UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    @Autowired
    private IMailService mailService;

    @Override
    public Account find(Long accountId) {
        return accountRepository.findOne(accountId);
    }

    @Override
    public Account enable(Long accountId) {
        return changeEnablement(accountId, true);
    }

    @Override
    public Account disable(Long accountId) {
        return changeEnablement(accountId, false);
    }


    @Override
    public void checkCurrentAccount(Account account) throws TradeException {
        if(account==null){
            throw new TradeException("Empty account");
        }
        Account currentAccount = getStrictlyLoggedAccount();

        if(!account.getId().equals(currentAccount.getId())){
            throw new TradeException("Passed account is not current");
        }
    }

    @Override
    public Account getStrictlyLoggedAccount()  {
        Account account = this.getCurrentAccount();
        if(account==null){
            throw new TradeException("You should be logged in.");
        }
        return account;
    }

    @Override
    public Account getCurrentAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            return null;
        }

        Object principal = authentication.getPrincipal();
        if(principal instanceof Account){
            Account account = (Account) principal;
            account = accountRepository.findOne(account.getId());
            return account;
        }

        return null;
    }

    private Account changeEnablement(Long accountId, boolean enabled) {
        Account account = accountRepository.findOne(accountId);
        account.setEnabled(enabled);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Page<Account> findAll(Integer pageNumber) {
        if(pageNumber==null){
            pageNumber = 0;
        }
        return accountRepository.findAll(new PageRequest(pageNumber, 20));
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UserDetails account = accountRepository.findByMail(mail);
        if(account==null){
            throw new UsernameNotFoundException("Not registered");
        }
        return account;
    }

    @Override
    public Account confirmRegistration(String guid) throws TradeException {

        Account account = accountRepository.findAccountByGuidAndConfirmed(guid, false);

        if (account == null) {
            throw new TradeException("Account with this guid " + guid + " not found (or already confirmed)!");
        }

        account.setConfirmed(true);

        accountRepository.save(account);
        return account;
    }

    @Override
    public Account cancelRegistration(String guid) throws TradeException {
        Account account = accountRepository.findAccountByGuidAndConfirmed(guid, false);

        if (account == null) {
            throw new TradeException("Account with this guid " + guid + " not found (or already confirmed)!");
        }

        account.setCanceled(true);

        accountRepository.save(account);
        return account;
    }

    @Override
    public Account createRealAccount(String login, String pwd, String phone, String currency) throws TradeException {
        Account account = buildAccount(login, pwd, phone, currency);
        //TODO remove after smpt
        account.setConfirmed(true);
        account.setEnabled(true);
        accountRepository.save(account);
        //TODO enable after restart
        //mailService.sendRegistrationMail(account);
        login(account);
        return account;
    }



    @Override
    public Account unfreezeAmount(Account account, BigDecimal amount) {
        account.setFrozenBalance(account.getFrozenBalance().subtract(amount));
        accountRepository.save(account);
        return account;
    }

    @Override
    public AccountDTO getCurrentAccountDTO() {
        Account account = getCurrentAccount();
        if(account==null) {
            return null;
        }

        //TODO performance hell fix
        BigDecimal openedOrdersSum  = tradeOrderService.getAllOpenedTradesSum(account);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setMail(account.getMail());
        accountDTO.setBalance(account.getBalance().subtract(account.getFrozenBalance()).subtract(openedOrdersSum));

        return accountDTO;
    }

    @Override
    public Account freezeAmount(Account account, BigDecimal amount) {
        account.setFrozenBalance(account.getFrozenBalance().add(amount));
        accountRepository.save(account);
        return account;
    }

    @Override
    public Account buildAccount(String mail, String pwd, String phone, String curr) throws TradeException {
        Account anotherAccount = accountRepository.findByMail(mail);
        if (anotherAccount != null) {
            throw new TradeException("Can't create account with this login!");
        }

        pwd = passwordEncoder.encodePassword(pwd, null);
        Account account = new Account(mail, pwd);

        account.setBalance(new BigDecimal("0.0"));
        account.setFrozenBalance(new BigDecimal("0.0"));
        account.setPhone(phone);
        account.setGuid(UUID.randomUUID().toString());
        account.setRole(Account.F_ROLE_ACCOUNT);

        return account;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account create(Account account) {
       return accountRepository.save(account);
    }

    @Override
    public Account findByMail(String login) {
        return accountRepository.findByMail(login);
    }

    @Override
    public Account login(Account account) {
        Authentication auth =
                new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return account;
    }

    @Override
    @Transactional
    public Account updateBalance(Account account, BigDecimal addedValue) {

        Account rereadAccount = find(account.getId());

        if (addedValue != null) {

            BigDecimal balance = rereadAccount.getBalance().add(addedValue);
            balance = balance.setScale(2, BigDecimal.ROUND_HALF_UP);
            rereadAccount.setBalance(balance);

           return accountRepository.save(rereadAccount);

        } else {
            logger.warn("Can't update balance of account {} because addedValue is null", account.getId());
        }
        return  rereadAccount;
    }
}
