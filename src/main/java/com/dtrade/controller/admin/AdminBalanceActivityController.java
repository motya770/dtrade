package com.dtrade.controller.admin;

import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.service.IBalanceActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by kudelin on 3/5/17.
 */

@Controller
@RequestMapping(value = "/admin/balanceactivity")
public class AdminBalanceActivityController {

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        List<BalanceActivity> balanceActivities = balanceActivityService.findAll();
        model.addAttribute("balanceActivities", balanceActivities);
        return "/admin/balanceactivity/list";
    }

}