package com.dtrade;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kudelin on 8/25/16.
 */
@Configuration
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
   public void addViewControllers(ViewControllerRegistry registry) {
          registry.addViewController("/").setViewName("index");
          registry.addViewController("/customer").setViewName("customer");
    }
}
