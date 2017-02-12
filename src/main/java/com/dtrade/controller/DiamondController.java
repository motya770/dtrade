package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@RestController
@RequestMapping(value = "/diamond")
public class DiamondController {

    @Autowired
    private IDiamondService diamondService;

    @RequestMapping(value = "/buy")
    public Diamond buyDiamond(@RequestBody Diamond diamond, @RequestParam Long buyerId,
                              @RequestParam BigDecimal price) throws TradeException{
        return diamondService.preBuyDiamond(diamond, buyerId, price);
    }

    @RequestMapping(value = "/open-for-sale")
    public Diamond openForSaleDiamond(@RequestBody Diamond diamond, @RequestParam BigDecimal price) throws TradeException{
        return diamondService.openForSale(diamond, price);
    }

    @RequestMapping(value = "/available")
    public List<Diamond> getAvailableDiamonds() {
        return diamondService.getAvailable();
    }

    @RequestMapping(value = "/my-owned")
    public List<Diamond> getMyDiamondsOwned() {
        return diamondService.getMyDiamondsOwned();
    }

    @RequestMapping(value = "/my-for-sale")
    public List<Diamond> getMyDiamondsForSale() {
        return diamondService.getMyDiamondsForSale();
    }
}
