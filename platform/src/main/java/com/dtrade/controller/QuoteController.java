package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.quote.depth.DepthQuote;
import com.dtrade.service.IQuotesService;
import com.dtrade.service.IQuotesServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by matvei on 1/23/15.
 */
@RestController
@RequestMapping(value = "/graph")
public class QuoteController {

    private static final Logger logger = LoggerFactory.getLogger(QuoteController.class);

    @Autowired
    private IQuotesService quotesService;

    @Autowired
    private IQuotesServiceProxy quotesServiceProxy;

    @RequestMapping(value = "/get-landing-quotes", method = RequestMethod.POST)
    public Map<String, String> getLandingQuotes() throws TradeException {
        return quotesService.getLandingQuotes();
    }

    @RequestMapping(value = "/get-depth-quotes", method = RequestMethod.GET)
    public String getDepthQuotes(@RequestParam(required = true) Diamond diamond
    ) throws TradeException {

        long start = System.currentTimeMillis();
        String pair =  quotesServiceProxy.getDepthQuotes(diamond);
        //System.out.println("depth quote difference: " + (System.currentTimeMillis() - start));
        return pair;
    }

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
    public String getRangeQuotes(@RequestParam(required = true) Diamond diamond,
                                      @RequestParam(required = false) Long start,
                                      @RequestParam(required = false) Long end
    ) throws TradeException {

        long startRequest = System.currentTimeMillis();
        String quotes = quotesService.getRangeQuotes(diamond, start, end);
//        if(start==null){
//            start = System.currentTimeMillis();
//        }
        //logger.info("time:  size: " + quotes.size());

        logger.debug("execution date: {}", (System.currentTimeMillis() - startRequest));

       // Stream.of().forEach();
        return quotes;
    }
}
