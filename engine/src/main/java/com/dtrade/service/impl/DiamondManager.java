package com.dtrade.service.impl;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.service.IDiamondManager;
import com.dtrade.service.IDiamondService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class DiamondManager implements IDiamondManager {

    @Autowired
    private IDiamondService diamondService;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    @EventListener(ContextRefreshedEvent.class)
    public void init(){
        Runnable runnable = ()->{
            try {
                diamondService.getAllAvailable("").forEach(diamond -> {
                    Long lastUpdated = diamond.getLastRoboUpdated();
                    if (lastUpdated != null) {
                        //TODO fix timeout
                        if (lastUpdated + (60_000 * 5) < System.currentTimeMillis()) {
                            logger.info("Hidding pair because its not updated {}", diamond.getId());
                            diamond.setDiamondStatus(DiamondStatus.ROBO_HIDDEN);
                            diamondService.unsecuredUpdate(diamond);
                        }
                    }
                });
            }catch (Exception e){
              logger.error("{}", e );
            }
        };
        executorService.scheduleAtFixedRate(runnable, 1_000, 5_000, TimeUnit.MILLISECONDS);
    }

}
