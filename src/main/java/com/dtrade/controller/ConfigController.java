package com.dtrade.controller;

import com.dtrade.model.config.Config;
import com.dtrade.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/config")
public class ConfigController {

    @Autowired
    private IConfigService configService;

    @GetMapping(value = "/get")
    public Config getActiveConfig(){
        return configService.getActiveConfig();
    }
}



