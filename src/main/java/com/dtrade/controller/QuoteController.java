package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/quote")
public class QuoteController {

    @Autowired
    private IQuotesService quotesService;


}
