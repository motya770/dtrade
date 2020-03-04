package com.dtrade;

import com.dtrade.config.SharedAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;


//@EnableDiscoveryClient
@SpringBootApplication
@Import(SharedAutoConfiguration.class)
public class BackofficeApplication implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {

    }

    public static void main(String[] args) {
        SpringApplication.run(BackofficeApplication.class, args);
    }
}
