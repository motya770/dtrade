package com.dtrade.controller;

import com.dtrade.service.IStockActivityService;
import org.springframework.beans.factory.annotation.Autowired;

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
