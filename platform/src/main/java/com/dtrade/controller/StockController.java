package com.dtrade.controller;

import com.dtrade.model.stock.StockDTO;
import com.dtrade.service.IStockService;

import java.util.List;

/**
 * Created by kudelin on 7/22/17.
 */
//@RestController
//@RequestMapping(value = "/stock")
public class StockController {

    //@Autowired
    private IStockService stockService;

    //@RequestMapping(value = "/owned", method = RequestMethod.POST)
    public List<StockDTO> getOwnedStocks(){
        return stockService.getStocksByAccount();
    }
}
