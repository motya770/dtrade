package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 12/4/16.
 */
public interface IBalanceActivityService {

    void createBalanceActivities(Account buyer, Account seller,
                                                                   TradeOrder buyOrder,
                                                                   TradeOrder sellOrder, BigDecimal amount, BigDecimal price);

    BalanceActivity createDepositBalanceActivity(CoinPayment coinPayment);

    BalanceActivity createWithdrawBalanceActivity(CoinPayment coinPayment);

    Page<BalanceActivity> getAccountBalanceActivities(Integer pageInteger);

    Page<BalanceActivity> findAll(Integer pageNumber);

    List<BalanceActivity> getDeposits(Account account);

    List<BalanceActivity> getWithdraws(Account account);

}
