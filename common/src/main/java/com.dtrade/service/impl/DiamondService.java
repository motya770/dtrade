package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondDTO;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class DiamondService implements IDiamondService {

    private static final Logger logger = LoggerFactory.getLogger(DiamondService.class);


    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IScoreService scoreService;

    private static final BigDecimal LOW_BORDER_PERCENT = new BigDecimal("0.997");
    private static final BigDecimal HIGH_BORDER_PERCENT = new BigDecimal("1.003");

    @Override
    public void validateDiamondCanTrade(Diamond diamond) {

        Long lastUpdated =  diamond.getLastRoboUpdated();
        if(lastUpdated==null || (System.currentTimeMillis() - lastUpdated > 60_000)) {

            if(diamond.getDiamondStatus().equals(DiamondStatus.ENLISTED)){
                diamond.setDiamondStatus(DiamondStatus.ROBO_HIDDEN);
                diamond = diamondRepository.save(diamond);
            }
        }

        if(lastUpdated!=null && (System.currentTimeMillis() - lastUpdated < 60_000)){
            if(diamond.getDiamondStatus().equals(DiamondStatus.ROBO_HIDDEN)){
                diamond.setDiamondStatus(DiamondStatus.ENLISTED);
                diamond = diamondRepository.save(diamond);
            }
        }

        if(!diamond.getDiamondStatus().equals(DiamondStatus.ENLISTED)){
            throw new TradeException("Can't open trade because pair in status: " + diamond.getDiamondStatus());
        }
    }

    @Override
    public Diamond defineRobotBorders(Diamond diamond, BigDecimal bid, BigDecimal ask) {
        diamond = diamondRepository.findById(diamond.getId()).get();

        if(bid==null){
            throw new TradeException("Can't define bid for " + diamond.getName());
        }

        if(ask==null){
            throw new TradeException("Can't define ask for " + diamond.getName());
        }


        logger.info("ask, bid1: " + bid + " " + ask);

        BigDecimal low = bid.multiply(LOW_BORDER_PERCENT);
        BigDecimal high = low.multiply(HIGH_BORDER_PERCENT);

        logger.info("ask, bid2: " + low + " " + high);

        diamond.setRoboLowEnd(low);
        diamond.setRoboHighEnd(high);
        diamond.setLastRoboUpdated(System.currentTimeMillis());

        return diamondRepository.save(diamond);
    }

    @Override
    public Diamond update(Diamond diamond) {
        if (!accountService.getStrictlyLoggedAccount().isAdmin()) {
            checkDiamondOwnship(accountService.getStrictlyLoggedAccount(), diamond);
        }
        return diamondRepository.save(diamond);
    }

    @Override
    public void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException{

        if(!account.getId().equals(diamond.getAccount().getId())){
            throw new TradeException("This diamond doesn't belong to owner");
        }
    }

    @Override
    public Diamond create(Diamond diamond) {
        //Diamond diamond = new Diamond();
        diamond.setDiamondStatus(DiamondStatus.CREATED);
        diamond.setAccount(accountService.getStrictlyLoggedAccount());

        diamond.setScore(scoreService.calculateScore(diamond));

        diamond = diamondRepository.save(diamond);
        return diamond;
    }

    @Override
    public List<Diamond> getEnlistedOrRoboHidden() {
        return diamondRepository.getEnlistedAndRoboHidded();
    }

    @Override
    public Diamond find(Long diamondId) {
        return diamondRepository.findById(diamondId).get();
    }

    @Override
    public Page<Diamond> getAllDiamonds() {
        return diamondRepository.findAll(new PageRequest(0, 100));
    }

    @Override
    public List<DiamondDTO> getAllAvailableDTO(String name){

        List<Diamond> diamonds = getAllAvailable(name);
        if(diamonds==null){
            return null;
        }

        List<DiamondDTO> diamondDTOS = new LinkedList<>();
        diamonds.forEach((diamond)->{
            DiamondDTO diamondDTO = new DiamondDTO();
            BeanUtils.copyProperties(diamond, diamondDTO);
            diamondDTOS.add(diamondDTO);

        });
        return diamondDTOS;
    }

    @Override
    public List<Diamond> getAllAvailable(String name) {
        if(StringUtils.isEmpty(name)){
            name = ""; //all enlisted diamonds
        }

        List<Diamond> diamonds = diamondRepository.getAllAvailableByName(name);
        return diamonds;
    }

    //@Override
    public List<Diamond> getAvailable() {
        return diamondRepository.getAllAvailableByName("");
    }


    @Override
    public List<Diamond> getDiamondsByScoreBounds(int lowerBound, int upperBound) {
        return diamondRepository.getDiamondsByScoreBounds(lowerBound, upperBound);
    }
}
