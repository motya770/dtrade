package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.service.IQuotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by matvei on 1/23/15.
 */
@RestController
@RequestMapping(value = "/graph")
public class GraphController {

    private static final Logger logger = LoggerFactory.getLogger(GraphController.class);

    @Autowired
    private IQuotesService quotesService;


    @RequestMapping(value = "/get-quotes-last-year", method = RequestMethod.POST)
    public List<Quote> getLastYearQuotes(@RequestParam(required = true) Diamond diamond
    ) throws TradeException {

        //TODO add implementation
        return null;
    }

    @RequestMapping(value = "/get-market-index-activity", method = RequestMethod.POST)
    public List<Quote> getMarketIndexActivity(@RequestParam(required = true) Diamond diamond
    ) throws TradeException {

        //TODO add implementation
        return null;
    }

    @RequestMapping(value = "/get-quotes", method = RequestMethod.POST)
    public List<Quote> getRangeQuotes(@RequestParam(required = true) Diamond diamond,
                                      @RequestParam(required = false) Long start,
                                      @RequestParam(required = false) Long end
    ) throws TradeException {

        long startRequest = System.currentTimeMillis();
        List<Quote> quotes = quotesService.getRangeQuotes(diamond, start, end);
//        if(start==null){
//            start = System.currentTimeMillis();
//        }
        logger.info("time:  size: " + quotes.size());

        System.out.println("execution date: " +  (System.currentTimeMillis() - startRequest));

       // Stream.of().forEach();
        return quotes;
    }
}
