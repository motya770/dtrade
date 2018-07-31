package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * Created by kudelin on 12/4/16.
 */
public interface IBalanceActivityService {

    /*
    @Deprecated
    void createBalanceActivity(Account buyer, Account seller, Diamond diamond, BigDecimal price) throws TradeException;

    @Deprecated
    BalanceActivity createBuyBalanceActivity(TradeOrder tradeOrder);
    */

    void createBalanceActivities(Account buyer, Account seller,
                                                                   TradeOrder buyOrder,
                                                                   TradeOrder sellOrder, BigDecimal amount, BigDecimal price);

    BalanceActivity createDepositBalanceActivity(CoinPayment coinPayment);

    BalanceActivity createWithdrawBalanceActivity(CoinPayment coinPayment);

    Page<BalanceActivity> getAccountBalanceActivities(Integer pageInteger);

    //TODO add paging and protecting
    Page<BalanceActivity> findAll(Integer pageNumber);
}
