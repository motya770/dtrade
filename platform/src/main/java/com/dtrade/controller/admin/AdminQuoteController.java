package com.dtrade.controller.admin;


import com.dtrade.exception.TradeException;
import com.dtrade.model.quote.Quote;
import com.dtrade.service.IQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by matvei on 1/13/15.
 */
@Controller
@RequestMapping(value = "/admin/quote")
public class AdminQuoteController {

    @Autowired
    private IQuotesService quotesService;

    @RequestMapping(value = "/history")
    public String history(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                          @RequestParam(required = false, defaultValue = "100") Integer pageSize,
                          @RequestParam(required = false) Sort sorting,
                          Model model) throws TradeException{

        Page<Quote> quotes = quotesService.getPagedQuotes(pageNumber, pageSize, sorting);
        model.addAttribute("quotes", quotes);
        return "admin/quote/list";
    }
}
