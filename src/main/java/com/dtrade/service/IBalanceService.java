package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.tradeorder.TradeOrder;

import java.math.BigDecimal;

public interface IBalanceService {

    Account updateRoboBalances(Currency currency, Account account);

    Account unfreezeAmount(Currency currency, Account account, BigDecimal amount);

    Account freezeAmount(Currency currency, Account account, BigDecimal amount);

    Account updateOpenSum(TradeOrder tradeOrder, Account account, BigDecimal amount);

    Account updateBalance(Currency currency, Account account, BigDecimal addedValue);

    Balance updateOpenSum(Currency currency, Account account, BigDecimal amount);

    Balance updateFrozenBalance(Currency currency, Account account, BigDecimal amount);

    BigDecimal getBalance(Currency currency, Account account);

    BigDecimal getActualBalance(Currency currency, Account account);

    Balance createBalance();

    BigDecimal getOpenSum(Currency currency, Account account);

    BigDecimal getFrozen(Currency currency, Account account);
}
