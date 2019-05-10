package com.dtrade;

import com.dtrade.config.SharedAutoConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(SharedAutoConfiguration.class)
@SpringBootApplication
public class SimulatorApplication implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {

    }

    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
    }
}
