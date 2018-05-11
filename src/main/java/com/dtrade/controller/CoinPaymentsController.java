package com.dtrade.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/coin-payment/", method = RequestMethod.POST)
public class CoinPaymentsController {

    @RequestMapping(value = "/notify")
    public void notifyNew(HttpServletRequest httpServletRequest) {
        //return categoryTickService.getByScore(score);
        System.out.println("NOTIFY: " + httpServletRequest);
    }
}
