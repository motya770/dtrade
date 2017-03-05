package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.diamond.Diamond;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 12/4/16.
 */
public interface IBalanceActivityService {


    void createBalanceActivity(Account buyer, Account seller, Diamond diamond, BigDecimal price) throws TradeException;

    List<BalanceActivity> getAccountBalanceActivities();

    List<BalanceActivity> findAll();


}
