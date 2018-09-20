package com.dtrade.controller;

import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kudelin on 8/29/16.
 */
@Controller
public class PageController {

    @RequestMapping(value = "/trade", method = RequestMethod.GET)
    public String trade(){
        return "index2";
    }

    @RequestMapping(value = "/basic", method = RequestMethod.GET)
    public String simple(){
        return "basic";
    }

    @Autowired
    private IDiamondService diamondService;

    @RequestMapping(value = "/coin", method = RequestMethod.GET)
    public String coin(){
        return "coin";
    }

    /*dc
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "test";
    }*/

    @RequestMapping(value = "/login-page", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(){
        return "main"; //"ico/index";
    }

    @RequestMapping(value = "/widget", method = RequestMethod.GET)
    public String widget(){
        return "widget";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }
}
