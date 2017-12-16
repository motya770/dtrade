package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IAccountService {

    void checkCurrentAccount(Account account) throws TradeException;

    Account getCurrentAccount();

    Account getStrictlyLoggedAccount();

    Account enable(Long accountId);

    Account disable(Long accountId);

    List<Account> findAll();

    Account find(Long accountId);

    void create(Account account);

    Account confirmRegistration(String guid) throws TradeException;

    Account cancelRegistration(String guid) throws TradeException;

    Account createRealAccount(String login, String pwd, String phone, String currency) throws TradeException;

    void save(Account account);

    void updateBalance(Account account, BigDecimal addedValue);

    Account findByMail(String login);


}
