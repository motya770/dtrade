package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.diamondactivity.DiamondActivity;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.repository.diamondactivity.DiamondActivityRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondActivityService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private IDiamondActivityService diamondActivityService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Override
    public Diamond buyDiamond(Diamond diamond) throws TradeException {

        Account from = accountService.getCurrentAccount();
        if(from==null){
            throw new TradeException("You should be logged in.");
        }

        balanceActivityService.createBalanceActivity(from, diamond);
        diamondActivityService.createDiamondActivity(from, diamond);

        diamond.setDiamondStatus(DiamondStatus.ACQUIRED);
        diamond.setAccount(from);
        diamond = diamondRepository.save(diamond);

        return diamond;
    }

    @Override
    public Diamond sellDiamond(Diamond diamond) {
        // FIXME: 9/8/16
        return diamond;
    }

    @Override
    public Diamond create(Diamond diamond) {
        //Diamond diamond = new Diamond();
        diamond.setDiamondStatus(DiamondStatus.ENLISTED);
        diamond = diamondRepository.save(diamond);
        return diamond;
    }



    @Override
    public List<Diamond> getAvailable() {
        return diamondRepository.getAvailable();
    }

    @Override
    public List<Diamond> getMyDiamondsForSale() {
        return diamondRepository.getMyDiamondsForSale();
    }

    @Override
    public List<Diamond> getMyDiamondsOwned() {

        Account account = accountService.getCurrentAccount();
        if(account == null){
            return null;
        }

        return diamondRepository.getMyDiamondsOwned(account.getId());
    }
}
