package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.offering.Offering;
import com.dtrade.model.offering.OfferingStatus;
import com.dtrade.model.offering.OfferingType;
import com.dtrade.repository.offering.OfferringRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by kudelin on 1/4/17.
 */
@Service
@Transactional
public class OfferingService implements IOfferingService {

    @Autowired
    private OfferringRepository offerringRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IOfferingService offeringService;

    @Override
    public Offering createOffering(Long fromAccountId, Long toAccountId, Long diamondId, BigDecimal price) throws TradeException {

        Account fromAccount = accountService.find(fromAccountId);

        accountService.checkCurrentAccount(fromAccount);

        Account toAccount = accountService.find(toAccountId);
        Diamond diamond = diamondService.find(diamondId);

        diamondService.checkDiamondOwnship(toAccount, diamond);

        Offering offering = new Offering();
        offering.setFrom(fromAccount);
        offering.setTo(toAccount);
        offering.setPrice(price);
        offering.setDiamond(diamond);
        offering.setOfferingType(OfferingType.ONE_DAY);
        offering.setOfferingStatus(OfferingStatus.MADE);

        return offerringRepository.save(offering);
    }

    @Override
    public Offering acceptOffering(Long offeringId, Long accountId) throws TradeException{

        Account account = accountService.find(accountId);

        accountService.checkCurrentAccount(account);

        Offering offering = offerringRepository.findOne(offeringId);

        if(!offering.getTo().equals(account)){
            throw new TradeException("This offering don't belong to this account");
        }

        //TODO add sell functionality
        return null;
    }

    @Override
    public  Offering rejectOffering(Long offeringId, Long accountId) throws TradeException
    {
        return null;
    }


    @Override
    public Offering cancelOffering(Long offeringId, Long accountId) throws TradeException{
        return null;
    }
}
