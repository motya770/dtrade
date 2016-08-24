package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
public class DiamondService implements IDiamondService {

    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private IAccountService accountService;

    @Override
    public List<Diamond> getOwned(){

        Account account = accountService.getCurrentAccount();

        if(account == null){
            return null;
        }

        return diamondRepository.getOwned(account.getId());
    }
}
