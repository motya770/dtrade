package com.dtrade;

import com.dtrade.config.SharedAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@Import(SharedAutoConfiguration.class)
@EnableDiscoveryClient
@SpringBootApplication
public class DtradeApplication  implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {

    }

    public static void main(String[] args) {
        SpringApplication.run(DtradeApplication.class, args);
    }
}
