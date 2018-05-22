package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@RestController
@RequestMapping(value = "/diamond", method = RequestMethod.POST)
public class DiamondController {

    @Autowired
    private IDiamondService diamondService;

    /*
    @Deprecated
    @RequestMapping(value = "/buy")
    public Diamond buyDiamond(@RequestBody Diamond diamond, @RequestParam Long buyerId,
                              @RequestParam BigDecimal price) throws TradeException{
        return diamondService.preBuyDiamond(diamond, buyerId, price);
    }

    @Deprecated
    @RequestMapping(value = "/hide-from-sale")
    public Diamond hideFromSale(@RequestBody Diamond diamond) throws TradeException{
        return diamondService.hideFromSale(diamond);
    }

    @Deprecated
    @RequestMapping(value = "/open-for-sale")
    public Diamond openForSaleDiamond(@RequestBody Diamond diamond, @RequestParam BigDecimal price) throws TradeException{
        return diamondService.openForSale(diamond, price);
    }
    */

    @RequestMapping(value = "/available")
    public List<Diamond> getAllAvailableDiamonds(@RequestParam(required = false) String name) {
        List<Diamond> diamonds = diamondService.getAllAvailable(name);
        return diamonds;
    }

/*
    @RequestMapping(value = "/my-owned")
    public List<Diamond> getMyDiamondsOwned() {
        return diamondService.getMyDiamondsOwned();
    }

    @RequestMapping(value = "/my-for-sale")
    public List<Diamond> getMyDiamondsForSale() {
        return diamondService.getMyDiamondsForSale();
    }
*/

}
