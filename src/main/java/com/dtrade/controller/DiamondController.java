package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.diamond.DiamondRepository;
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
    private DiamondRepository diamondRepository;

    @Autowired
    private IDiamondService diamondService;

    @RequestMapping(value = "/buy")
    public Diamond buy(@RequestBody Diamond diamond){
        return diamondService.buy(diamond);
    }

    @RequestMapping(value = "/sell")
    public Diamond sell(@RequestBody Diamond diamond){
        return diamondService.sell(diamond);
    }

    @RequestMapping(value = "/available")
    public List<Diamond> getAvailableDiamonds() {
        return diamondRepository.getAvailable();
    }

    @RequestMapping(value = "/owned")
    public List<Diamond> getOwned() {
        return diamondService.getOwned();
    }

    @RequestMapping(value = "/sale")
    public List<Diamond> getSale() {
        return diamondRepository.getSale();
    }
}
