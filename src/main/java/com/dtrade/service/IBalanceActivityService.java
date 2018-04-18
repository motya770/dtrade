package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

/**
 * Created by kudelin on 12/4/16.
 */
public interface IBalanceActivityService {

    @Deprecated
    void createBalanceActivity(Account buyer, Account seller, Diamond diamond, BigDecimal price) throws TradeException;

    Pair<BalanceActivity, BalanceActivity> createBalanceActivities(Account buyer, Account seller, BigDecimal cash, TradeOrder buyOrder, TradeOrder sellOrder);

    Page<BalanceActivity> getAccountBalanceActivities(Integer pageInteger);

    //TODO add paging and protecting
    Page<BalanceActivity> findAll(Integer pageNumber);
}
