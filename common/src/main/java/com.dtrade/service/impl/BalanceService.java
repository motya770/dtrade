package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.SimpleQuote;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.balance.BalanceRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceService;

//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.IMap;

import com.dtrade.service.IBookOrderServiceProxy;
import com.dtrade.service.IDiamondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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

   // @Autowired
   // private HazelcastInstance hazelcastInstance;

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
        return updateBalance(balance);
        //return balanceRepository.save(balance);
    }

    @Override
    public Balance updateFrozenBalance(Currency currency, Account account, BigDecimal amount){
        Balance balance = getBalance(currency, account);
        balance.setFrozen(balance.getFrozen().add(amount));
        return updateBalance(balance);
        //return balanceRepository.save(balance);
    }


    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @Override
    public List<Balance> getBalancesByAccount(Account account){
        List<Balance> balances =  account.getBalances();
        balances.forEach(balance -> {
            if(!balance.getCurrency().equals(Currency.USD)){
                Diamond diamond =  diamondService.getDiamondByCurrency(balance.getCurrency());
                SimpleQuote simpleQuote = bookOrderServiceProxy.getSpread(diamond).block();
                BigDecimal bidPrice = simpleQuote.getBid();
                BigDecimal sum = balance.getAmount().multiply(bidPrice);
                balance.setSellSum(sum);
            }else {
                balance.setSellSum(balance.getAmount());
            }
        });
        return balances;
    }

    @Override
    public List<Currency> getBaseCurrencies(){
        return Arrays.stream(Currency.values()).filter(c->c.isBaseCurrency()).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public List<Balance> getBaseBalancesByAccount(Account account){
        return balanceRepository.getBaseBalancesByAccount(account, getBaseCurrencies());
    }

    @Override
    @Transactional
    public Balance updateBalance(Balance balance) {

        Balance savedBalance = balanceRepository.save(balance);
        return savedBalance;

//        IMap<Long, Long> balancesMap = hazelcastInstance.getMap("balancesMap");
//        Long balanceId = balance.getId();
//        //create
//        if(balanceId==null){
//            balance =  balanceRepository.save(balance);
//            balancesMap.put(balance.getId(), balance.getId());
//            return balance;
//        //update
//        }else {
//
//            if (!balancesMap.isLocked(balanceId)) {
//
//                balancesMap.put(balanceId, balanceId);
//                balancesMap.lock(balanceId);
//
//                Balance savedBalance = balanceRepository.save(balance);
//
//                balancesMap.unlock(balanceId);
//
//                return savedBalance;
//
//            } else {
//                throw new TradeException("Can't update current balance because its is locked" + balance.getId());
//            }
//        }
    }

    @Override
    @Transactional
    public Balance updateBalance(Currency currency, Account account, BigDecimal addedValue) {
        Balance balance = getBalance(currency, account);
        balance.setAmount(balance.getAmount().add(addedValue));
        return updateBalance(balance);
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


    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Balance getBalance(Currency currency, Account account){

        try {

            if (currency == null) {
                throw new TradeException("Currency not defined.");
            }

            Balance balance = balanceRepository.getBalance(account, currency);
            if (balance == null) {
                balance = createBalance(account, currency);
            }
            return balance;
        }catch (Throwable e){
            logger.error("erroor {}", e);
        }
        return null;
    }
}
