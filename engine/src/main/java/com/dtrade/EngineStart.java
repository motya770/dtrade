package com.dtrade;

import com.dtrade.service.core.ITradeEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class EngineStart  implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(EngineStart.class);

    @Autowired
    private ITradeEngine tradeEngine;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("run");
        tradeEngine.prepareAndLaunch();
    }
}