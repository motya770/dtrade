package com.dtrade.controller;

import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.diamondactivity.DiamondActivity;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kudelin on 3/1/17.
 */
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IDiamondActivityService diamondActivityService;

    @RequestMapping(value = "/balance-activities", method = RequestMethod.GET)
    public List<BalanceActivity> getBalanceActivities(Model model){
        return balanceActivityService.getAccountBalanceActivities();
    }

    @RequestMapping(value = "/diamond-activities", method = RequestMethod.GET)
    public List<DiamondActivity> getDiamondActivity(Model model){
        return diamondActivityService.getAccountDiamondActivities();
    }
}
