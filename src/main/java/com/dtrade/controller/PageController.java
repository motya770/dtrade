package com.dtrade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kudelin on 8/29/16.
 */
@Controller
public class PageController {

    @RequestMapping(value = "/index.html", method = RequestMethod.GET)
    public String index(){
        return "index2";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(){
        return "registration";
    }
}
