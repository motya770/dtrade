package com.dtrade.controller;

import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondActivityService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private IDiamondService diamondService;

    /*
    @RequestMapping(value = "/upload-diamond", method = RequestMethod.POST)
    public Diamond uploadNewDiamond(Model model){
        return null;
    }

    @RequestMapping(value = "/balance-activities", method = RequestMethod.POST)
    public Page<BalanceActivity> getBalanceActivities(Model model){
        return balanceActivityService.getAccountBalanceActivities(0);
    }

    @RequestMapping(value = "/diamond-activities", method = RequestMethod.POST)
    public List<DiamondActivity> getDiamondActivity(Model model){
        return diamondActivityService.getAccountDiamondActivities();
    }*/
}
