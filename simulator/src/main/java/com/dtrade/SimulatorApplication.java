package com.dtrade;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimulatorApplication implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {

    }

    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
    }
}
