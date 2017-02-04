package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class AccountService implements IAccountService, UserDetailsService {


    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

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
        Account currentAccount = getStrictlyLoggedAccount();
        if(account.equals(currentAccount)){
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

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof Account){
            return (Account) principal;
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
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UserDetails account = accountRepository.findByMail(mail);
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
        accountRepository.save(account);

        mailService.sendRegistrationMail(account);

        return account;
    }

    private Account buildAccount(String mail, String pwd, String phone, String curr) throws TradeException {
        Account anotherAccount = accountRepository.findByMail(mail);
        if (anotherAccount != null) {
            throw new TradeException("Can't create account with this login!");
        }

        pwd = passwordEncoder.encodePassword(pwd, null);
        Account account = new Account(mail, pwd);

        account.setPhone(phone);
        account.setGuid(UUID.randomUUID().toString());

        return account;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void create(Account account) {
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void updateBalance(Account account, BigDecimal addedValue) {

        Account rereadAccount = find(account.getId());

        if (addedValue != null) {

            BigDecimal balance = rereadAccount.getBalance().add(addedValue);
            balance = balance.setScale(2, BigDecimal.ROUND_HALF_UP);
            rereadAccount.setBalance(balance);

            accountRepository.save(rereadAccount);

        } else {
            logger.warn("Can't update balance of account {} because addedValue is null", account.getId());
        }
    }
}
