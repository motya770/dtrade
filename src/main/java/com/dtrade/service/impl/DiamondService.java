package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IAccountService;
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
public class DiamondService implements IDiamondService {

    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public Diamond create(Diamond diamond) {
        //Diamond diamond = new Diamond();
        diamond.setDiamondStatus(DiamondStatus.PREPARED);
        diamondRepository.save(diamond);
        return diamond;
    }

    @Override
    public List<Diamond> getOwned() {

        //Account account = accountService.getCurrentAccount();
        //Account account = new Account();
        //TODO fix
        //account.setId(0L);
        //if (account == null) {
          //  return null;
       // }

        return diamondRepository.getOwned();//(null);
    }
}
