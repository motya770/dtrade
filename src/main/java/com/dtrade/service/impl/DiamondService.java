package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.diamond.DiamondType;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
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


    @Autowired
    private IQuotesService quotesService;

    @Override
    public void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException{

        if(!account.equals(diamond.getAccount())){
            throw new TradeException("This diamond doesn't belong to owner");
        }
    }

    @Override
    public Diamond preBuyDiamond(Diamond diamond, Long buyerId, Long sellerId, BigDecimal price) throws TradeException {
        Account buyer = accountService.find(buyerId);
        Account seller = accountService.find(sellerId);
        diamond = diamondRepository.findOne(diamond.getId());

        return buyDiamond(diamond, buyer, seller, price);
    }

    @Override
    public Diamond buyDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException {

        accountService.checkCurrentAccount(buyer);
        return performTrade(diamond, buyer, seller, price);
    }

    @Override
    public Diamond sellDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException {

        accountService.checkCurrentAccount(seller);
        return performTrade(diamond, buyer, seller, price);
    }

    private Diamond performTrade(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException{

        checkDiamondOwnship(seller, diamond);

        quotesService.create(diamond, price, System.currentTimeMillis());

        balanceActivityService.createBalanceActivity(buyer, seller, diamond, price);
        diamondActivityService.createTradeActivity(buyer, seller, diamond);

        diamond.setDiamondStatus(DiamondStatus.ACQUIRED);
        diamond.setAccount(buyer);
        diamond = diamondRepository.save(diamond);

        return diamond;
    }

    @Override
    public Diamond openForSale(Diamond diamond, BigDecimal price) throws TradeException {

       diamond  = diamondRepository.findOne(diamond.getId());

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
        diamond.setAccount(accountService.getStrictlyLoggedAccount());
        diamond = diamondRepository.save(diamond);
        return diamond;
    }

    @Override
    public Diamond find(Long diamondId) {
        return diamondRepository.findOne(diamondId);
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
    public BigDecimal calculateScore(Diamond diamond) {

        //TODO write formula for scoring

         //DiamondType diamondType = diamond.getDiamondType();

         BigDecimal carats = diamond.getCarats();

         BigDecimal clarity = diamond.getClarity();

         return  carats.multiply(clarity);
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
