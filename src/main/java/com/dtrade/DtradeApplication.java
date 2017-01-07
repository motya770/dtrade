package com.dtrade;

import com.dtrade.repository.offering.OfferringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import java.io.File;
//import java.nio.file.Files;

@SpringBootApplication
public class DtradeApplication  implements CommandLineRunner {


    @Autowired
    private OfferringRepository offerringRepository;

    @Override
    public void run(String... strings) throws Exception {
        //offerringRepository.findOne(1L);
        offerringRepository.getPreviousLiveOfferingsForDiamond(null, null);
    }



    public static void main(String[] args) {

        //File file = new File();
        //Files.readAllLines(file.toPath());

        SpringApplication.run(DtradeApplication.class, args);
    }
}
