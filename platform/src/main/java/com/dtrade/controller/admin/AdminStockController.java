package com.dtrade.controller.admin;

import com.dtrade.model.stock.Stock;
import com.dtrade.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by kudelin on 7/22/17.
 */
@Controller
@RequestMapping(value = "/admin/stock")
public class AdminStockController {

    @Autowired
    private IStockService stockService;

    @GetMapping(value = "/list")
    public String list(Model model) {
        List<Stock> stocks = stockService.findAll();
        model.addAttribute("stocks", stocks);
        return "/admin/stock/list";
    }
}