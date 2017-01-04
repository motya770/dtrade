package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondActivityService;
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
    private IDiamondActivityService diamondActivityService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    private void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException{

        if(!account.equals(diamond.getAccount())){
            throw new TradeException("You don't own this diamond");
        }
    }

    @Override
    public Diamond buyDiamond(Diamond diamond, BigDecimal price) throws TradeException {

        Account from = accountService.getStrictlyLoggedAccount();

        checkDiamondOwnship(from, diamond);

        balanceActivityService.createBalanceActivity(from, diamond, price);
        diamondActivityService.createTradeActivity(from, diamond);

        diamond.setDiamondStatus(DiamondStatus.ACQUIRED);
        diamond.setAccount(from);
        diamond = diamondRepository.save(diamond);

        return diamond;
    }

    @Override
    public Diamond openForSale(Diamond diamond, BigDecimal price) throws TradeException {

       //TODO reread diamond

       Account account = accountService.getStrictlyLoggedAccount();
       checkDiamondOwnship(account, diamond);

       DiamondStatus status = diamond.getDiamondStatus();
       if(!status.equals(DiamondStatus.ACQUIRED)){
           throw new TradeException("Diamond in status " + status + " but should be " + DiamondStatus.ACQUIRED);
       }

       diamond.setDiamondStatus(DiamondStatus.ENLISTED);
       diamond.setPrice(price);

       diamond = diamondRepository.save(diamond);
       diamondActivityService.openForSaleActivity(account, diamond);

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
    public List<Diamond> getAllAvailable() {
        return diamondRepository.getAllAvailable();
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
    public BigDecimal calculateScore() {
        return null;
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
