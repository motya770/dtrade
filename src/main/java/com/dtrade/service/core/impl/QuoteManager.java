package com.dtrade.service.core.impl;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IQuotesService;
import com.dtrade.service.core.IQuoteManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by kudelin on 12/13/16.
 */
@Service
public class QuoteManager implements IQuoteManager {


    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private IDiamondService diamondService;

    @PostConstruct
    private void init(){

        ExecutorService service = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> {
            System.out.println("Test");
        };

        service.submit(runnable);
    }


    @Override
    public void calculateQuotes() {

        List<Diamond> diamonds =  diamondService.getAllAvailable();
        diamonds.parallelStream().forEach(diamond -> {
            BigDecimal score = diamond.getScore();

            //TODO calculate price for that score.
            BigDecimal ask = null;
            BigDecimal bid = null;
            quotesService.create(diamond, ask, bid, System.currentTimeMillis());


        });
    }



}
