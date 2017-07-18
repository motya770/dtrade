package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private IScoreService scoreService;

    @Override
    public void update(Diamond diamond) {
        checkDiamondOwnship(accountService.getStrictlyLoggedAccount(), diamond);
        diamondRepository.save(diamond);
    }

    @Override
    public void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException{

        if(!account.getId().equals(diamond.getAccount().getId())){
            throw new TradeException("This diamond doesn't belong to owner");
        }
    }

    @Override
    public Diamond preBuyDiamond(Diamond diamond, Long buyerId, BigDecimal price) throws TradeException {

        diamond = diamondRepository.findOne(diamond.getId());

        Account buyer = accountService.find(buyerId);
        Account seller = accountService.find(diamond.getAccount().getId());
        diamond = diamondRepository.findOne(diamond.getId());

        return buyDiamond(diamond, buyer, seller, price);
    }

    @Override
    public Diamond buyDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException {

        accountService.checkCurrentAccount(buyer);
        diamond = performTrade(diamond, buyer, seller, price);

        scoreService.calculateCategory(diamond);

        return diamond;
    }

    @Override
    public Diamond sellDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException {

        accountService.checkCurrentAccount(seller);
        return performTrade(diamond, buyer, seller, price);
    }

    private void checkDiamondPrice(Diamond diamond, BigDecimal price){

        if(diamond.getPrice().compareTo(price) != 0){
            throw new TradeException("Proposed price and diamond price is not equal!");
        }
    }

    private Diamond performTrade(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException{

        checkDiamondOwnship(seller, diamond);
        checkDiamondPrice(diamond, price);
//
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
       //BigDecimal bid = diamond.getPrice();

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

       //BigDecimal ask = price;
       quotesService.create(diamond, price, System.currentTimeMillis());

       return diamond;
    }

    @Override
    public Diamond hideFromSale(Diamond diamond) {

        diamond = diamondRepository.findOne(diamond.getId());

        Account account = accountService.getStrictlyLoggedAccount();
        checkDiamondOwnship(account, diamond);

        DiamondStatus status = diamond.getDiamondStatus();

        if(!status.equals(DiamondStatus.ENLISTED)){
            throw new TradeException("Diamond in status " + status + " but should be " + DiamondStatus.ENLISTED);
        }

        diamond.setDiamondStatus(DiamondStatus.ACQUIRED);
        diamond = diamondRepository.save(diamond);

        diamondActivityService.hideFromSaleActivity(account, diamond);

        return diamond;
    }

    @Override
    public Diamond create(Diamond diamond) {
        //Diamond diamond = new Diamond();
        diamond.setDiamondStatus(DiamondStatus.ENLISTED);
        diamond.setAccount(accountService.getStrictlyLoggedAccount());

        diamond.setScore(scoreService.calculateScore(diamond));

        diamond = diamondRepository.save(diamond);
        return diamond;
    }

    @Override
    public Diamond find(Long diamondId) {
        return diamondRepository.findOne(diamondId);
    }

    @Override
    public Page<Diamond> getAllDiamonds() {
        return diamondRepository.findAll(new PageRequest(0, 100));
    }

    @Override
    public List<Diamond> getAllAvailable() {
        return diamondRepository.getAllAvailable();
    }

    @Override
    public List<Diamond> getAvailable() {

        return diamondRepository.getAllAvailable();

        /*Account account = accountService.getCurrentAccount();
        if(account==null){
           return diamondRepository.getAvailable();
        }
        else{
           return diamondRepository.getAvailableExceptCurrent(account.getId());
        }*/
    }

    @Deprecated
    @Override
    public List<Diamond> getMyDiamondsForSale() {
        return diamondRepository.getMyDiamondsForSale(accountService.getStrictlyLoggedAccount().getId());
    }


    @Deprecated
    @Override
    public List<Diamond> getMyDiamondsOwned() {

        Account account = accountService.getCurrentAccount();
        if(account == null){
            return null;
        }

        return diamondRepository.getMyDiamondsOwned(account.getId());
    }

    @Override
    public List<Diamond> getDiamondsByScoreBounds(int lowerBound, int upperBound) {
        return diamondRepository.getDiamondsByScoreBounds(lowerBound, upperBound);
    }
}
