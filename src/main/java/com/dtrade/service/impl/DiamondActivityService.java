package com.dtrade.service.impl;

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
    public List<DiamondActivity> findAll() {
        return diamondActivityRepository.findAll();
    }


}
