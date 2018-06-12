package com.dtrade.controller.admin;

import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.service.ICoinPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin/coinpayment")
public class AdminCoinPaymentController {

    @Autowired
    private ICoinPaymentService coinPaymentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(@RequestParam(required = false) Integer pageNumber, Model model) {

        Page<CoinPayment> coinPayments = coinPaymentService.findAll(pageNumber);
        model.addAttribute("coinPayments", coinPayments);
        return "/admin/coinpayment/list";
    }
}