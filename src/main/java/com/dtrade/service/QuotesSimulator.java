package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.diamond.DiamondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class QuotesSimulator {

    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private IQuotesService quotesService;

    public void produce() {
        Thread thread = new Thread() {
            @Override
            public void run() {

                while (true) {
                    //send quotes
                    try {
                        List<Diamond> diamonds = diamondRepository.findAll();
                        diamonds.parallelStream().forEach((diamond) -> {

                            BigDecimal value = new BigDecimal(Math.ceil(Math.random() * 100));
                            BigDecimal price = value.setScale(2, BigDecimal.ROUND_DOWN);
                            Long timestamp = System.currentTimeMillis();

                            quotesService.create(diamond, price, timestamp);
                        });
                        Thread.sleep(60_500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
}
