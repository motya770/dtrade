package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@RestController
@RequestMapping(value = "/diamonds")
public class DiamondController {

    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private IDiamondService diamondService;

    @RequestMapping(value = "/available")
    public List<Diamond> getAvailableDiamonds(){
        return diamondRepository.getAvailable();
    }

    @RequestMapping(value = "/owned")
    public List<Diamond> getOwned(){
        return diamondService.getOwned();

    }

    public List<Diamond> getSale(){
        return diamondRepository.getSale();
    }
}
