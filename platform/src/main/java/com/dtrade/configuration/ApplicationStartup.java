package com.dtrade.configuration;

//import com.dtrade.service.simulators.QuotesSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by kudelin on 9/6/16.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

//    @Autowired
//    private QuotesSimulator quotesSimulator;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.debug("Application started!");
       // quotesSimulator.produce();
    }

}