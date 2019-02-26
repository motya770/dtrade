package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.SimpleQuote;
import com.dtrade.model.stat.Stat;
import com.dtrade.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatService implements IStatService {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceService balanceService;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @Autowired
    private IDiamondService diamondService;

    @Override
    public Stat getProfitLoss() {
        Account account = accountService.getStrictlyLoggedAccount();
        List<Balance> balances = account.getBalances();

//        balances.forEach(balance -> {
//            if(!balance.getCurrency().equals(Currency.USD)){
//                Diamond diamond =  diamondService.getDiamondByCurrency(balance.getCurrency());
//                SimpleQuote simpleQuote = bookOrderServiceProxy.getSpread(diamond).block();
//                BigDecimal bidPrice = simpleQuote.getBid();
//                BigDecimal sum = balance.getAmount().multiply(bidPrice);
//                balance.setSellSum(sum);
//            }
//        });

        //TODO currently only USD
        /*
        List<BalanceActivity> deposits = balanceActivityService.getDeposits();
        List<BalanceActivity> withdraws = balanceActivityService.getWithdraws();

        Function<List<BalanceActivity>, BigDecimal> func =  (activities) -> activities.stream().
                filter(ba->ba.getCurrency().equals(Currency.USD))
                .map(balanceActivity -> balanceActivity.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal depositsSum =  func.apply(deposits);
        BigDecimal withdrawsSum = func.apply(withdraws);

        */

//        BigDecimal withdrawsSum = BigDecimal.ZERO;
//        deposits.stream().filter(ba->ba.getCurrency().equals(Currency.USD)).forEach(balanceActivity -> {
//            withdrawsSum.add(balanceActivity.getAmount());
//        });


        return null;
    }
}
