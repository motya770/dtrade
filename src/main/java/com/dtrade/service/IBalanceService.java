package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.tradeorder.TradeOrder;

import java.math.BigDecimal;
import java.util.List;

public interface IBalanceService {

    List<Balance> getBalancesByAccount(Account account);

    Balance updateBalance(Balance balance);

    Balance updateRoboBalances(Currency currency, Account account);

    Balance unfreezeAmount(Currency currency, Account account, BigDecimal amount);

    Balance freezeAmount(Currency currency, Account account, BigDecimal amount);

    void updateOpenSum(TradeOrder tradeOrder, Account account, BigDecimal sum, BigDecimal amount);

    Balance updateBalance(Currency currency, Account account, BigDecimal addedValue);

    Balance updateOpen(Currency currency, Account account, BigDecimal amount);

    Balance updateFrozenBalance(Currency currency, Account account, BigDecimal amount);

    Balance getBalance(Currency currency, Account account);

    BigDecimal getActualBalance(Currency currency, Account account);

    BigDecimal getOpenSum(Currency currency, Account account);

    BigDecimal getFrozen(Currency currency, Account account);
}
