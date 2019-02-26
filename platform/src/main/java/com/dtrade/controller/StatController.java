package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.service.IStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/stat")
public class StatController {

    @Autowired
    private IStatService statService;


}
