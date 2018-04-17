package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/quote")
public class QuoteController {

    @Autowired
    private IQuotesService quotesService;

    @RequestMapping(value = "/get-diamond-last-quotes", method = RequestMethod.POST)
    public List<Pair<?, ?>> getLastQuotesForDiamonds(@RequestBody(required = true) ArrayList<Diamond> diamonds){
        return quotesService.getLastQuoteForDiamonds(diamonds);
    }
}
