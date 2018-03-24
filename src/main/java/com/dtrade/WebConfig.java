package com.dtrade;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kudelin on 8/25/16.
 */
@Configuration
@ComponentScan
@EnableCaching
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
   public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/diamonds").setViewName("diamonds");
        registry.addViewController("/account").setViewName("account");
        registry.addViewController("/").setViewName("index2");
        registry.addViewController("/old").setViewName("index");
        registry.addViewController("/customer").setViewName("customer");
    }
}
