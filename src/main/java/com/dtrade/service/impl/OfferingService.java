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

    @Override
    public Offering createOffering(Long fromAccountId, Long toAccountId, Long diamondId, BigDecimal price) throws TradeException {

        Account fromAccount = accountService.find(fromAccountId);

        accountService.checkCurrentAccount(fromAccount);

        Account toAccount = accountService.find(toAccountId);
        Diamond diamond = diamondService.find(diamondId);

        diamondService.checkDiamondOwnship(toAccount, diamond);

        Offering offering = new Offering();
        offering.setSeller(fromAccount);
        offering.setBuyer(toAccount);
        offering.setPrice(price);
        offering.setDiamond(diamond);
        offering.setOfferingType(OfferingType.ONE_DAY);
        offering.setOfferingStatus(OfferingStatus.MADE);

//        offerringRepository.getPreviousLiveOfferingsForDiamond(diamond, offering)
//                .forEach((CheckedFunction<Offering>) o-> {
//            this.cancelOffering(o.getId(), o.getBuyer().getId());
//        });

        return offerringRepository.save(offering);
    }

    @Override
    public Offering acceptOffering(Long offeringId, Long accountId) throws TradeException{

        Account account = accountService.find(accountId);
        Offering offering = offerringRepository.findOne(offeringId);

        checkOffering(offering, account, true);


        offering.setOfferingStatus(OfferingStatus.ACCEPTED);
        offering = offerringRepository.save(offering);

        diamondService.sellDiamond(offering.getDiamond(), offering.getBuyer(), offering.getSeller(), offering.getPrice());

        return offering;
    }


    private void checkOffering(Offering offering, Account account, boolean toAccount) throws TradeException{

        accountService.checkCurrentAccount(account);

        Account myAccount = toAccount ?  offering.getBuyer() : offering.getSeller();

        if (!myAccount.equals(account)){
            throw new TradeException("This offering don't belong buyer this owner");
        }

        if(!offering.getOfferingStatus().equals(OfferingStatus.MADE)){
            throw new TradeException("You can reject only new offering");
        }

        Long creationDate = offering.getCreateDate();
        Long duration = offering.getOfferingType().getDuration();
        if((creationDate + duration) < System.currentTimeMillis()){
            throw new TradeException("Offering type is expired");
        }
    }

    @Override
    public  Offering rejectOffering(Long offeringId, Long accountId) throws TradeException
    {
        Account account = accountService.find(accountId);
        Offering offering = offerringRepository.findOne(offeringId);

        checkOffering(offering, account, true);

        offering.setOfferingStatus(OfferingStatus.REJECTED);
        return offerringRepository.save(offering);
    }

    @Override
    public Offering cancelOffering(Long offeringId, Long accountId) throws TradeException{
        Account account = accountService.find(accountId);
        Offering offering = offerringRepository.findOne(offeringId);

        checkOffering(offering, account, false);

        offering.setOfferingStatus(OfferingStatus.CANCELED);
        return offerringRepository.save(offering);
    }
}
