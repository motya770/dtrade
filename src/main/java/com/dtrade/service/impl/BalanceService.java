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

@Transactional
@Service
public class BalanceService  implements IBalanceService{

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public Account updateRoboBalances(Currency currency, Account account){
        if(!account.isRoboAccount()){
             throw new TradeException("This is not robo account: " + account.getMail());
        }

        if(account.getBalance()==null) {
            Balance balance = createBalance();
            account.setBalance(balance);
            account = accountService.save(account);
        }

        BigDecimal b = getActualBalance(currency, account);
        if(b.compareTo(BigDecimal.ZERO)==-1 || b.compareTo(BigDecimal.ZERO) == 0) {
            account = updateBalance(currency, account, new BigDecimal("100000"));
        }
        return account;
    }

    @Override
    public Account unfreezeAmount(Currency currency, Account account, BigDecimal amount) {
        updateFrozenBalance(currency, account, amount.multiply(new BigDecimal("-1")));
        return account;
    }

    @Override
    public Account freezeAmount(Currency currency, Account account, BigDecimal amount) {
        updateFrozenBalance(currency, account, amount);
        return account;
    }

    @Override
    public Account updateOpenSum(TradeOrder tradeOrder, Account account, BigDecimal amount) {

        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {
            Balance balance = updateOpenSum(tradeOrder.getDiamond().getCurrency(), account, amount);
            account.setBalance(balance);
        }

        return account;
    }

    @Override
    public Balance updateOpenSum(Currency currency, Account account, BigDecimal amount) {

        Balance balance =  balanceRepository.findById(account.getBalance().getId()).get();

        if(currency.equals(Currency.USD)){

            balance.setUsdOpen(balance.getUsdOpen().add(amount));

        }else if(currency.equals(Currency.BTC)) {

            balance.setBitcoinOpen(balance.getBitcoinOpen().add(amount));

        }else if(currency.equals(Currency.ETH)) {

            balance.setEtherOpen((balance.getEtherOpen().add(amount)));

        }else{
            throw new TradeException("Currency not defined.");
        }
        return balanceRepository.save(balance);
    }

    @Override
    public Balance updateFrozenBalance(Currency currency, Account account, BigDecimal amount){

        if(currency.equals(Currency.USD)){

             account.getBalance().setUsdFrozen(account.getBalance().getUsdFrozen().add(amount));

        }else if(currency.equals(Currency.BTC)) {

            account.getBalance().setBitcoinFrozen(account.getBalance().getBitcoinFrozen().add(amount));

        }else if(currency.equals(Currency.ETH)) {

            account.getBalance().setEtherFrozen((account.getBalance().getEtherFrozen().add(amount)));

        }else{
            throw new TradeException("Currency not defined");
        }
        return  balanceRepository.save(account.getBalance());
    }

    @Override
    @Transactional
    public Account updateBalance(Currency currency, Account account, BigDecimal addedValue) {

        Balance balanceObj = balanceRepository.findById(account.getBalance().getId()).get();

        System.out.println("updating balance: " + balanceObj.getId() + " " + Thread.currentThread().getName());

        if(currency.equals(Currency.USD)){

            BigDecimal balance = balanceObj.getUsdAmount().add(addedValue);
            balance = balance.setScale(2, BigDecimal.ROUND_HALF_UP);
            balanceObj.setUsdAmount(balance);

        }else if(currency.equals(Currency.BTC)) {

            BigDecimal balance = balanceObj.getBitcoinAmount().add(addedValue);
            balance = balance.setScale(8, BigDecimal.ROUND_HALF_UP);
            balanceObj.setBitcoinAmount(balance);

        }else if(currency.equals(Currency.ETH)) {

            BigDecimal balance = balanceObj.getEtherAmount().add(addedValue);
            balance = balance.setScale(8, BigDecimal.ROUND_HALF_UP);
            balanceObj.setEtherAmount(balance);

        }else{
            logger.warn("Can't update balance of account {} because addedValue is null", account.getId());
        }

        balanceRepository.save(balanceObj);
        account.setBalance(balanceObj);
        return account;
    }

    @Override
    public BigDecimal getOpenSum(Currency currency, Account account) {
        if (currency.equals(Currency.USD)) {

            return account.getBalance().getUsdOpen();

        } else if (currency.equals(Currency.BTC)) {

            return account.getBalance().getBitcoinOpen();

        } else if (currency.equals(Currency.ETH)) {

            return account.getBalance().getEtherOpen();

        } else {
            throw new TradeException("Currency not defined. ");
        }
    }

    @Override
    public BigDecimal getFrozen(Currency currency, Account account){
        if(currency.equals(Currency.USD)){

            return account.getBalance().getUsdFrozen();

        }else if(currency.equals(Currency.BTC)) {

            return account.getBalance().getBitcoinFrozen();

        }else if(currency.equals(Currency.ETH)) {

            return account.getBalance().getEtherFrozen();

        }else{
            throw new TradeException("Currency not defined");
        }
    }

    @Override
    public Balance createBalance() {
        Balance balance = new Balance();

        balance.setBitcoinAmount( BigDecimal.ZERO);
        balance.setUsdAmount(BigDecimal.ZERO);
        balance.setEtherAmount(BigDecimal.ZERO);

        balance.setBitcoinFrozen(BigDecimal.ZERO);
        balance.setUsdFrozen(BigDecimal.ZERO);
        balance.setEtherFrozen(BigDecimal.ZERO);

        balance.setBitcoinOpen(BigDecimal.ZERO);
        balance.setEtherOpen(BigDecimal.ZERO);
        balance.setUsdOpen(BigDecimal.ZERO);

        balance = balanceRepository.save(balance);
        return balance;
    }

    @Override
    public BigDecimal getActualBalance(Currency currency, Account account) {

        BigDecimal balance = getBalance(currency, account);
        BigDecimal frozen = getFrozen(currency, account);
        BigDecimal openSum = getOpenSum(currency, account);
        return balance.subtract(frozen).subtract(openSum);
    }

    @Override
    public BigDecimal getBalance(Currency currency, Account account) {
        if(currency.equals(Currency.USD)){

            return account.getBalance().getUsdAmount();

        }else if(currency.equals(Currency.BTC)) {

            return account.getBalance().getBitcoinAmount();

        }else if(currency.equals(Currency.ETH)) {

            return account.getBalance().getEtherAmount();

        }else{
            throw new TradeException("Currency not defined. ");
        }
    }
}
