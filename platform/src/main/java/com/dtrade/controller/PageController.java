package com.dtrade.controller;

import com.dtrade.model.account.Account;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by kudelin on 8/29/16.
 */
@Controller
public class PageController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/trade", method = RequestMethod.GET)
    public String trade(){
        return "index2";
    }

    /*
    @RequestMapping(value = "/basic", method = RequestMethod.GET)
    public String simple(){
        return "basic";
    }*/

    @CrossOrigin
    @RequestMapping(value = "/info/presentation", method = RequestMethod.GET)
    public String pdf(){
        return "redirect:https://static.wixstatic.com/ugd/c5b996_6997d84d2d9d435c815ec972828f3112.pdf";
    }

    /*
    @RequestMapping(value = "/coin", method = RequestMethod.GET)
    public String coin(){
        return "coin";
    }*/

    @RequestMapping(value = "/referral", method = RequestMethod.GET)
    public String referral(@RequestParam(required = false) String myRef, Model model){
        Account account = null;
        if(!StringUtils.isEmpty(myRef)) {
             account = accountService.findByReferral(myRef);
        }else{
             account = accountService.getStrictlyLoggedAccount();
        }
        model.addAttribute("account", account);
        return "referral";
    }

    /*
    @RequestMapping(value = "/login-page", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(){
        return "login";
    }*/

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainProd(){
        return "redirect:https://www.resistapp.io/"; //"ico/index";
    }


    @RequestMapping(value = "/widget", method = RequestMethod.GET)
    public String widget(){
        return "widget";
    }

    /*
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }*/
}
