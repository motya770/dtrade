package com.dtrade;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by kudelin on 8/25/16.
 */
@Configuration
@ComponentScan
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

      @Override
   public void addViewControllers(ViewControllerRegistry registry) {
          registry.addViewController("/").setViewName("index");
   }
}
