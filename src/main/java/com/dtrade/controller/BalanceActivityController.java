package com.dtrade.controller;

import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.service.IBalanceActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/balance-activity", method = RequestMethod.POST)
public class BalanceActivityController {

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @RequestMapping(value = "/by-account")
    public List<BalanceActivity> getLiveOrdersByAccount(){
        return balanceActivityService.getAccountBalanceActivities();
    }
}
