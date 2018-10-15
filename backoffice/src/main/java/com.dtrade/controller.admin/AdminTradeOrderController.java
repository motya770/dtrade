package com.dtrade.controller.admin;

import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by kudelin on 7/22/17.
 */
@Controller
@RequestMapping(value = "/admin/tradeorder")
public class AdminTradeOrderController {


    @Autowired
    private ITradeOrderService tradeOrderService;


    @GetMapping(value = "/list")
    public String list(@RequestParam(required = false) Integer pageNumber, Model model) {

        Page<TradeOrder> tradeOrders = tradeOrderService.findAll(pageNumber);
        model.addAttribute("tradeOrders", tradeOrders);
        return "/admin/tradeorder/list";
    }
}
