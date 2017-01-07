package com.dtrade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import java.io.File;
//import java.nio.file.Files;

@SpringBootApplication
public class DtradeApplication {

    public static void main(String[] args) {

        //File file = new File();
        //Files.readAllLines(file.toPath());

        SpringApplication.run(DtradeApplication.class, args);
    }
}
