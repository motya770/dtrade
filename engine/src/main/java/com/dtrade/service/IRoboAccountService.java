package com.dtrade.service;

import com.dtrade.model.account.Account;

public interface IRoboAccountService {

    void createRoboAccounts();

    Account createRoboAccount(String email);
}
