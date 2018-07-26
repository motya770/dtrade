package com.dtrade;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kudelin on 8/25/16.
 */
@Configuration
@ComponentScan
@EnableCaching
public class WebConfig implements WebMvcConfigurer {

   public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/diamonds").setViewName("diamonds");
        registry.addViewController("/crypto").setViewName("diamonds");
        registry.addViewController("/account").setViewName("account");
        registry.addViewController("/trade").setViewName("index2");
        //registry.addViewController("/customer").setViewName("customer");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
