package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;

/**
 * Created by kudelin on 12/4/16.
 */
public interface IBalanceActivityService {


    void createBalanceActivity(Account from, Diamond diamond) throws TradeException;


}
