package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class AccountService implements IAccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

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

    private Account changeEnablement(Long accountId, boolean enabled){
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
        return findByMail(mail);
    }

    @Override
    public Account getCurrentAccount() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Account findByMail(String login){
        return accountRepository.findByMail(login);
    }
}
