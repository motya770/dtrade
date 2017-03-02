package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamondactivity.DiamondActivity;
import com.dtrade.model.diamondactivity.DiamondActivityType;
import com.dtrade.repository.diamondactivity.DiamondActivityRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondActivityService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class DiamondActivityService implements IDiamondActivityService {

    @Autowired
    private DiamondActivityRepository diamondActivityRepository;

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IAccountService accountService;

    @Override
    public void createTradeActivity(Account buyer, Account seller, Diamond diamond) throws TradeException {

        diamondService.checkDiamondOwnship(seller, diamond);

        DiamondActivity activity = new DiamondActivity();
        activity.setBuyer(buyer);
        activity.setDate(System.currentTimeMillis());
        activity.setDiamond(diamond);
        activity.setSeller(seller);
        activity.setPrice(diamond.getPrice());
        activity.setDiamondActivityType(DiamondActivityType.BUY_ACTIVITY);

        diamondActivityRepository.save(activity);
    }

    @Override
    public void openForSaleActivity(Account from, Diamond diamond) {

        DiamondActivity activity = new DiamondActivity();
        activity.setBuyer(null);
        activity.setSeller(from);
        activity.setDiamond(diamond);
        activity.setPrice(diamond.getPrice());
        activity.setDiamondActivityType(DiamondActivityType.OPEN_FOR_SALE_ACTIVITY);
        activity.setDate(System.currentTimeMillis());

        diamondActivityRepository.save(activity);
    }

    @Override
    public void hideFromSaleActivity(Account from, Diamond diamond) {
        DiamondActivity activity = new DiamondActivity();
        activity.setBuyer(null);
        activity.setSeller(from);
        activity.setDiamond(diamond);
        activity.setPrice(diamond.getPrice());
        activity.setDiamondActivityType(DiamondActivityType.HIDE_FROM_SALE_ACTIVITY);
        activity.setDate(System.currentTimeMillis());

        diamondActivityRepository.save(activity);
    }

    @Override
    public List<DiamondActivity> getAccountDiamondActivities() {
        Account account = accountService.getStrictlyLoggedAccount();
        return diamondActivityRepository.getAccountDiamondActivities(account);
    }

    @Override
    public List<DiamondActivity> findAll() {
        return diamondActivityRepository.findAll();
    }

}
