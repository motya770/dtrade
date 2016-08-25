package com.dtrade;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by kudelin on 8/25/16.
 */
@Configuration
@ComponentScan
public class WebConfig {//extends WebMvcAutoConfiguration {

    /*
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }*/
}
