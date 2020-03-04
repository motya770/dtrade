package com.dtrade;

import com.dtrade.config.SharedAutoConfiguration;
import com.dtrade.controller.AccountController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Slf4j
@Import(SharedAutoConfiguration.class)
//@EnableDiscoveryClient
@SpringBootApplication
public class DtradeApplication  implements CommandLineRunner {


    @Override
    public void run(String... strings) throws Exception {
        log.info("STARTED!!!");
    }

    public static void main(String[] args) {
        SpringApplication.run(DtradeApplication.class, args);
    }
}
