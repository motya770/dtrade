package com.dtrade.controller;

import com.dtrade.service.IStockActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping(value = "/stock-activity", method = RequestMethod.POST)
public class StockActivityController {

    @Autowired
    private IStockActivityService stockActivityService;
/*
    @RequestMapping(value = "/by-account")
    public List<StockActivity> getAccountBalanceActivities(){
        return stockActivityService.getAccountStockActivities();
    }*/
}
