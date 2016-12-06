package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamondactivity.DiamondActivity;
import com.dtrade.repository.diamondactivity.DiamondActivityRepository;
import com.dtrade.service.IDiamondActivityService;
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

    @Override
    public void createDiamondActivity(Account from,  Diamond diamond) {

        DiamondActivity activity = new DiamondActivity();
        activity.setBuyer(from);
        activity.setDate(System.currentTimeMillis());
        activity.setDiamond(diamond);
        activity.setSeller(diamond.getAccount());
        activity.setAmount(diamond.getPrice());

        diamondActivityRepository.save(activity);

    }


    @Override
    public List<DiamondActivity> findAll() {
        return diamondActivityRepository.findAll();
    }


}
