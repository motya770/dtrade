package com.dtrade.controller;

import com.dtrade.model.stock.Stock;
import com.dtrade.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kudelin on 7/22/17.
 */
@RestController
@RequestMapping(value = "/stock")
public class StockController {

    @Autowired
    private IStockService stockService;

    @RequestMapping(value = "/owned", method = RequestMethod.POST)
    public List<Stock> getOwnedStocks(){
        return stockService.getStocksByAccount();
    }

}
