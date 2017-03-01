package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamondactivity.DiamondActivity;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IDiamondActivityService {

    List<DiamondActivity> findAll();

    void createTradeActivity(Account buyer, Account seller, Diamond diamond) throws TradeException;

    void openForSaleActivity(Account from, Diamond diamond);

    void hideFromSaleActivity(Account from, Diamond diamond);

    List<DiamondActivity> getAccountDiamondActivities();

}
