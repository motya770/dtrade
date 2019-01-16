package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.account.AccountDTO;
import com.dtrade.model.diamond.Diamond;
import org.springframework.data.domain.Page;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IAccountService {

    Integer MAX_ROBO_ACCOUNT_COUNT = 100;

    Account findByReferral(String referral);

    AccountDTO createReferalAccount(String mail, String ref);



    String getRoboAccountMail(Diamond diamond, int rand);

    AccountDTO getCurrentAccountDTO();

    Account buildAccount(String mail, String pwd, String phone, String curr);

    void checkCurrentAccount(Account account) throws TradeException;

    Account getCurrentAccount();

    Account getStrictlyLoggedAccount();

    Account enable(Long accountId);

    Account disable(Long accountId);

    Page<Account> findAll(Integer pageNumber);

    Account find(Long accountId);

    Account create(Account account);

    Account confirmRegistration(String guid) throws TradeException;

    Account cancelRegistration(String guid) throws TradeException;

    Account createRealAccount(String login, String pwd, String phone, String currency) throws TradeException;

    Account save(Account account);

    Account findByMail(String login);

    Account login(Account account);

    Account loginByRef(String ref);

}
