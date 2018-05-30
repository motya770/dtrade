package com.dtrade.service.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.balanceactivity.BalanceActivityType;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.balanceactivity.BalanceActivityRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private IDiamondService diamondService;

    @Override
    public Page<BalanceActivity> findAll(Integer pageNumber) {
        if(pageNumber==null){
            pageNumber = 0;
        }
        return balanceActivityRepository.findAll(new PageRequest(pageNumber, 20));
    }

    @Override
    public Page<BalanceActivity> getAccountBalanceActivities(Integer pageInteger) {
        Account account = accountService.getStrictlyLoggedAccount();
        return balanceActivityRepository.getByAccount(account, new PageRequest(pageInteger, 10));
    }


    @Override
    public BalanceActivity createWithdrawBalanceActivity(CoinPayment coinPayment) {

        if(coinPayment.getAccount()==null){
            throw new TradeException("Account is null");
        }

        Account account = accountService.find(coinPayment.getAccount().getId());
        accountService.updateBalance(account, coinPayment.getInWithdrawRequest().getAmountUsd().multiply(new BigDecimal("-1")));

        accountService.unfreezeAmount(account, coinPayment.getInWithdrawRequest().getAmountUsd());

        BalanceActivity ba = new BalanceActivity();
        ba.setAccount(account);
        ba.setBalanceActivityType(BalanceActivityType.WITHDRAW);
        ba.setAmount(coinPayment.getDepositRequest().getAmountUsd());
        ba.setCreateDate(System.currentTimeMillis());
        ba.setBalanceSnapshot(account.getBalance());
        return balanceActivityRepository.save(ba);
    }

    @Override
    public BalanceActivity createDepositBalanceActivity(CoinPayment coinPayment) {

        if(coinPayment.getAccount()==null){
            throw new TradeException("Account is null");
        }

        Account account = accountService.find(coinPayment.getAccount().getId());
        accountService.updateBalance(account, coinPayment.getDepositRequest().getAmountUsd());

        BalanceActivity ba = new BalanceActivity();
        ba.setAccount(account);
        ba.setBalanceActivityType(BalanceActivityType.DEPOSIT);
        ba.setAmount(coinPayment.getDepositRequest().getAmountUsd());
        ba.setCreateDate(System.currentTimeMillis());
        ba.setBalanceSnapshot(account.getBalance());
        return balanceActivityRepository.save(ba);
    }

    @Override
    @Transactional(noRollbackFor = NotEnoughMoney.class)
    public org.springframework.data.util.Pair<BalanceActivity, BalanceActivity> createBalanceActivities(Account buyer, Account seller, BigDecimal cash, TradeOrder buyOrder, TradeOrder sellOrder) {

        //buyer don't have enough money
        if(buyer.getBalance().compareTo(cash) < 0){
            throw new NotEnoughMoney();
        }

        BigDecimal minusCash = cash.multiply(new BigDecimal("-1"));

        accountService.updateBalance(buyer, minusCash);
        accountService.updateBalance(seller, cash);

        BalanceActivity buyerActivity = new BalanceActivity();
        buyerActivity.setBalanceActivityType(BalanceActivityType.BUY);
        buyerActivity.setAccount(buyer);
        buyerActivity.setAmount(minusCash);
        buyerActivity.setCreateDate(System.currentTimeMillis());
        buyerActivity.setBuyOrder(buyOrder);
        buyerActivity.setBalanceSnapshot(buyer.getBalance());

        BalanceActivity sellerActivity = new BalanceActivity();
        sellerActivity.setBalanceActivityType(BalanceActivityType.SELL);
        sellerActivity.setAccount(seller);
        sellerActivity.setAmount(cash);
        sellerActivity.setCreateDate(System.currentTimeMillis());
        sellerActivity.setSellOrder(sellOrder);
        sellerActivity.setBalanceSnapshot(seller.getBalance());

        balanceActivityRepository.save(sellerActivity);
        balanceActivityRepository.save(buyerActivity);

        return org.springframework.data.util.Pair.of(buyerActivity, sellerActivity);
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
