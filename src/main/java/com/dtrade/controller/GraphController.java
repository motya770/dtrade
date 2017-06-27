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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by matvei on 1/23/15.
 */
@RestController
@RequestMapping(value = "/graph")
public class GraphController {

    private static final Logger logger = LoggerFactory.getLogger(GraphController.class);

    @Autowired
    private IQuotesService quotesService;

    @RequestMapping(value = "/get-quotes", method = RequestMethod.POST)
    public List<Quote> getRangeQuotes(@RequestParam(required = true) Diamond diamond,
                                      @RequestParam(required = false) Long openTime,
                                      @RequestParam(required = false) Long closeTime
    ) throws TradeException {

        long start = System.currentTimeMillis();
        List<Quote> quotes = quotesService.getRangeQuotes(diamond, openTime, closeTime);
        logger.info("time: " + (System.currentTimeMillis() - start) + " size: " + quotes.size());

        Arrays.asList("a", "b", "c").stream().collect(Collectors.toList());

       // Stream.of().forEach();
        return quotes;
    }
}
