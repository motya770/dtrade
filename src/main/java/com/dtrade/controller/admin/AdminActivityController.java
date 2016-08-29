package com.dtrade.controller.admin;

import com.dtrade.model.activity.Activity;
import com.dtrade.service.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by kudelin on 8/28/16.
 */
@Controller
@RequestMapping(value = "/admin/activity")
public class AdminActivityController {

    @Autowired
    private IActivityService activityService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Activity> list() {
        return activityService.findAll();
    }
}
