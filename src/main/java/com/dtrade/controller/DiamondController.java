package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@RestController
@RequestMapping(value = "/diamond")
public class DiamondController {


    @Autowired
    private IDiamondService diamondService;

    @RequestMapping(value = "/buy-diamond")
    public Diamond buyDiamond(@RequestBody Diamond diamond) throws TradeException{
        return diamondService.buyDiamond(diamond);
    }

    @RequestMapping(value = "/sell-diamond")
    public Diamond sellDiamond(@RequestBody Diamond diamond){
        return diamondService.sellDiamond(diamond);
    }

    @RequestMapping(value = "/available")
    public List<Diamond> getAvailableDiamonds() {
        return diamondService.getAvailable();
    }

    @RequestMapping(value = "/my-diamonds-owned")
    public List<Diamond> getMyDiamondsOwned() {
        return diamondService.getMyDiamondsOwned();
    }

    @RequestMapping(value = "/my-diamonds-for-sale")
    public List<Diamond> getMyDiamondsForSale() {
        return diamondService.getMyDiamondsForSale();
    }
}
