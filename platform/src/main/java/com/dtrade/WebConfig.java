package com.dtrade;

import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by kudelin on 8/25/16.
 */
@Configuration
@ComponentScan
@EnableCaching
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ConfigurableServletWebServerFactory tomcatCustomizer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> connector.addUpgradeProtocol(new Http2Protocol()));
        return factory;
    }

   public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/diamonds").setViewName("diamonds");
        registry.addViewController("/crypto").setViewName("diamonds");
        registry.addViewController("/account").setViewName("account");
        registry.addViewController("/trade").setViewName("index2");
        //registry.addViewController("/customer").setViewName("customer");
    }
}
