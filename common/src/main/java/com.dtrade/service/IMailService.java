package com.dtrade.service;


import com.dtrade.model.account.Account;

/**
 * Created by matvei on 6/7/15.
 */
public interface IMailService {
    void sendRegistrationMail(Account account);
}
