package com.dtrade.controller;

import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamondactivity.DiamondActivity;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondActivityService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping(value = "/upload-diamond", method = RequestMethod.POST)
    public Diamond uploadNewDiamond(Model model){
        //TODO add
        return null;
    }

    @RequestMapping(value = "/balance-activities", method = RequestMethod.POST)
    public List<BalanceActivity> getBalanceActivities(Model model){
        return balanceActivityService.getAccountBalanceActivities();
    }

    @RequestMapping(value = "/diamond-activities", method = RequestMethod.POST)
    public List<DiamondActivity> getDiamondActivity(Model model){
        return diamondActivityService.getAccountDiamondActivities();
    }
}
