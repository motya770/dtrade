package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.offering.Offering;
import com.dtrade.service.IOfferingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * Created by kudelin on 1/4/17.
 */


//@Controller
//@RequestMapping(value = "/offering", method = {RequestMethod.GET, RequestMethod.PUT})
public class OfferingController {

    /*
    @Autowired
    private IOfferingService offeringService;

    @RequestMapping(value = "/make-offer", method = RequestMethod.POST)
    public Offering makeOffer(@RequestParam Long fromId,
                              @RequestParam Long toId,
                              @RequestParam Long diamondId,
                              @RequestParam BigDecimal price
                              ) throws TradeException{
        return offeringService.createOffering(fromId, toId, diamondId, price);
    }*/

}
