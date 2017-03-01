package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.balanceactivity.BalanceActivityType;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.balanceactivity.BalanceActivityRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    @Autowired
    private IDiamondService diamondService;

    @Override
    public List<BalanceActivity> getAccountBalanceActivities() {
        Account account = accountService.getStrictlyLoggedAccount();
        return balanceActivityRepository.findByAccount(account);
    }

    @Override
    public void createBalanceActivity(Account buyer, Account seller,  Diamond diamond, BigDecimal price) throws TradeException{

        BigDecimal balance = buyer.getBalance();
        if(balance.doubleValue() < diamond.getPrice().doubleValue()){
            throw new TradeException("Not enough money for this operation.");
        }

        BigDecimal moneyToTake = price.multiply(new BigDecimal("-1"));
        accountService.updateBalance(buyer, moneyToTake);

        diamondService.checkDiamondOwnship(seller, diamond);

        BigDecimal moneyToGive = diamond.getPrice();
        accountService.updateBalance(seller, moneyToGive);

        BalanceActivity buyerActivity = new BalanceActivity();
        buyerActivity.setBalanceActivityType(BalanceActivityType.BUY);
        buyerActivity.setAccount(buyer);
        buyerActivity.setAmount(moneyToTake);
        buyerActivity.setCreateDate(System.currentTimeMillis());

        BalanceActivity sellerActivity = new BalanceActivity();
        sellerActivity.setBalanceActivityType(BalanceActivityType.SELL);
        sellerActivity.setAccount(seller);
        sellerActivity.setAmount(moneyToGive);
        sellerActivity.setCreateDate(System.currentTimeMillis());

        balanceActivityRepository.save(sellerActivity);
        balanceActivityRepository.save(buyerActivity);
    }
}
