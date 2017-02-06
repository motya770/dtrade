package com.dtrade.controller.admin;

import com.dtrade.model.diamondactivity.DiamondActivity;
import com.dtrade.service.IDiamondActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by kudelin on 8/28/16.
 */
@Controller
@RequestMapping(value = "/admin/diamondactivity")
public class AdminDiamondActivityController {

    @Autowired
    private IDiamondActivityService diamondActivityService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        List<DiamondActivity> diamondActivities = diamondActivityService.findAll();
        model.addAttribute("diamondActivities", diamondActivities);
        return "/admin/diamondactivity/list";
    }
}
