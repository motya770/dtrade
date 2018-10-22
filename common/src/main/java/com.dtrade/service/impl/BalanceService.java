package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.balance.BalanceRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalanceService  implements IBalanceService{

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private IAccountService accountService;

    @Transactional
    @Override
    public Balance updateRoboBalances(Currency currency, Account account){

        if(!account.isRoboAccount()){
             throw new TradeException("This is not robo account: " + account.getMail());
        }

        Balance balance = null;
        BigDecimal b = getActualBalance(currency, account);
        if(b.compareTo(BigDecimal.ZERO) <= 0) {
            balance = updateBalance(currency, account, new BigDecimal("100000"));
        }
        return balance;
    }

    @Override
    public Balance unfreezeAmount(Currency currency, Account account, BigDecimal amount) {
        return updateFrozenBalance(currency, account, amount.multiply(new BigDecimal("-1")));
    }

    @Override
    public Balance freezeAmount(Currency currency, Account account, BigDecimal amount) {
        return updateFrozenBalance(currency, account, amount);
    }

    @Override
    public void updateOpenSum(TradeOrder tradeOrder, Account account, BigDecimal sum, BigDecimal amount) {
        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {
            updateOpen(tradeOrder.getDiamond().getBaseCurrency(), account, sum);
        }else if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)) {
            updateOpen(tradeOrder.getDiamond().getCurrency(), account, amount);
        }
    }

    @Override
    public Balance updateOpen(Currency currency, Account account, BigDecimal amount) {

        Balance balance = getBalance(currency, account);
        balance.setOpen(balance.getOpen().add(amount));
        return balanceRepository.save(balance);
    }

    @Override
    public Balance updateFrozenBalance(Currency currency, Account account, BigDecimal amount){
        Balance balance = getBalance(currency, account);
        balance.setFrozen(balance.getFrozen().add(amount));
        return balanceRepository.save(balance);
    }

    @Override
    public List<Balance> getBalancesByAccount(Account account){
        return account.getBalances();
    }

    @Override
    public List<Currency> getBaseCurrencies(){
        return Arrays.stream(Currency.values()).filter(c->c.isBaseCurrency()).collect(Collectors.toList());
    }

    @Override
    public List<Balance> getBaseBalancesByAccount(Account account){
        return balanceRepository.getBaseBalancesByAccount(account, getBaseCurrencies());
    }

    @Override
    @Transactional
    public Balance updateBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    @Transactional
    public Balance updateBalance(Currency currency, Account account, BigDecimal addedValue) {
        Balance balance = getBalance(currency, account);
        balance.setAmount(balance.getAmount().add(addedValue));
        return balanceRepository.save(balance);
    }

    @Override
    public BigDecimal getOpenSum(Currency currency, Account account) {
       return getBalance(currency, account).getOpen();
    }

    @Override
    public BigDecimal getFrozen(Currency currency, Account account){
       Balance balance = getBalance(currency, account);
       return balance.getFrozen();
    }

    private Balance createBalance(Account account, Currency currency) {

        if(account==null){
            throw new TradeException("Account is null. Can't create balance");
        }

        if(currency==null){
            throw new TradeException("Currency is null for " + account.getMail());
        }

        Balance balance = new Balance();
        balance.setAmount(BigDecimal.ZERO);
        balance.setFrozen(BigDecimal.ZERO);
        balance.setCurrency(currency);
        balance.setOpen(BigDecimal.ZERO);
        balance.setAccount(account);
        balance = balanceRepository.save(balance);
        return balance;
    }

    @Override
    public BigDecimal getActualBalance(Currency currency, Account account) {
        Balance balance = getBalance(currency, account);
        return balance.getDTO().getBalance();
    }

    @Override
    public Balance getBalance(Currency currency, Account account){

        try {

            if (currency == null) {
                throw new TradeException("Currency not defined.");
            }

           // System.out.println("account: " + account.getMail() + " " + currency + " " + Thread.currentThread().getName());
            //Balance balance =  account.getBalances().stream().filter((b)->b.getCurrency()
              ///      .equals(currency)).findFirst().orElse(null);
            Balance balance = balanceRepository.getBalance(account, currency);
            if (balance == null) {
                return createBalance(account, currency);
            }
            return balance;
        }catch (Throwable e){
            System.out.println("erroor " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}