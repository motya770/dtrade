package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.activity.Activity;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.repository.activity.ActivityRepository;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class DiamondService implements IDiamondService {

    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public Diamond buy(Diamond diamond) throws TradeException {

        Account account = accountService.getCurrentAccount();
        if(account==null){
            throw new TradeException("You should be logged in.");
        }

        BigDecimal balance = account.getBalance();
        if(balance.doubleValue() < diamond.getPrice().doubleValue()){
            throw new TradeException("Not enought money for this operation.");
        }

        account.getBalance().subtract(diamond.getPrice());

        Activity activity = new Activity();
        activity.setBuyer(account);
        activity.setDate(System.currentTimeMillis());
        activity.setDiamond(diamond);
        activity.setSeller(diamond.getAccount());
        activity.setSum(diamond.getPrice());

        diamond.setAccount(account);

        accountRepository.save(account);
        activityRepository.save(activity);
        diamond = diamondRepository.save(diamond);

        return diamond;
    }

    @Override
    public Diamond sell(Diamond diamond) {
        // FIXME: 9/8/16
        return diamond;
    }

    @Override
    public Diamond create(Diamond diamond) {
        //Diamond diamond = new Diamond();
        diamond.setDiamondStatus(DiamondStatus.PREPARED);
        diamondRepository.save(diamond);
        return diamond;
    }

    @Override
    public List<Diamond> getOwned() {

        Account account = accountService.getCurrentAccount();
        if(account == null){
            return null;
        }

        return diamondRepository.getOwned(account.getId());
    }
}
