package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.balanceactivity.BalanceActivityType;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamondactivity.DiamondActivity;
import com.dtrade.repository.balanceactivity.BalanceActivityRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by kudelin on 12/4/16.
 */
@Service
@Transactional
public class BalanceActivityService implements IBalanceActivityService {

    @Autowired
    private BalanceActivityRepository balanceActivityRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public void createBalanceActivity(Account from, Diamond diamond) throws TradeException{

        BigDecimal balance = from.getBalance();
        if(balance.doubleValue() < diamond.getPrice().doubleValue()){
            throw new TradeException("Not enough money for this operation.");
        }

        BigDecimal moneyToTake = diamond.getPrice().multiply(new BigDecimal(-1));
        accountService.updateBalance(from, moneyToTake);

        Account to = diamond.getAccount();
        if(to==null){
            throw new TradeException("Diamond should be bound with owner.");
        }

        BigDecimal moneyToGive = diamond.getPrice();
        accountService.updateBalance(to, moneyToGive);


        BalanceActivity fromActivity = new BalanceActivity();
        fromActivity.setBalanceActivityType(BalanceActivityType.BUY);
        fromActivity.setAccount(from);
        fromActivity.setAmount(moneyToTake);


        BalanceActivity toActivity = new BalanceActivity();
        toActivity.setBalanceActivityType(BalanceActivityType.SELL);
        toActivity.setAccount(to);
        toActivity.setAmount(moneyToGive);

        balanceActivityRepository.save(toActivity);
        balanceActivityRepository.save(fromActivity);


    }
}
