package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.diamond.DiamondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;


@Component
public class QuotesSimulator {

    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private IQuotesService quotesService;

    private TransactionTemplate transactionTemplate;


    @Autowired
    @Qualifier(value = "transactionManager")
    public void setTransactionManager(PlatformTransactionManager transactionManager){

        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Transactional
    public void produce() {
        Thread thread = new Thread() {
            @Override
            public void run() {

                while (true) {
                    //send quotes
                    transactionTemplate.execute((action)->{
                        try {
                            List<Diamond> diamonds = diamondRepository.findAll();
                            diamonds.parallelStream().forEach((diamond) -> {

                                BigDecimal value = new BigDecimal(Math.ceil(Math.random() * 100));
                                BigDecimal price = value.setScale(2, BigDecimal.ROUND_DOWN);
                                Long timestamp = System.currentTimeMillis();

                               // quotesService.create(diamond, price, timestamp);
                            });
                            Thread.sleep(60_500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    });

                }
            }
        };
        thread.start();
    }
}
