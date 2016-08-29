package com.dtrade.service.impl;

import com.dtrade.model.activity.Activity;
import com.dtrade.repository.activity.ActivityRepository;
import com.dtrade.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class ActivityService implements IActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }


}
