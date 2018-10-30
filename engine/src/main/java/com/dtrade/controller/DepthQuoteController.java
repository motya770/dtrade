package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.depth.DepthQuote;
import com.dtrade.service.IDepthQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping(value = "/graph", method = RequestMethod.POST)
public class DepthQuoteController {

    @Autowired
    private IDepthQuotesService depthQuotesService;

    @RequestMapping(value = "/get-depth-quotes", method = RequestMethod.POST)
    public Pair<List<DepthQuote>, List<DepthQuote>> getDepthQuotes(@RequestBody(required = true) Diamond diamond
    ) throws TradeException {
        //long start = System.currentTimeMillis();
        Pair<?, ?> pair =  depthQuotesService.getDepthQuotes(diamond);
        return (Pair<List<DepthQuote>, List<DepthQuote>>)pair;
    }
}
