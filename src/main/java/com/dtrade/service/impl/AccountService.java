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

/**
 * Created by kudelin on 8/24/16.
 */
@Service
public class AccountService implements IAccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

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
