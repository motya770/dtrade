package com.dtrade.controller;

import com.dtrade.service.IQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/quote")
public class QuoteController {

    @Autowired
    private IQuotesService quotesService;


}
