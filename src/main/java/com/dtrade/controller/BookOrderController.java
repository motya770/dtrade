package com.dtrade.controller;


import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IBookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/book-order", method = RequestMethod.POST)
public class BookOrderController {

    @Autowired
    private IBookOrderService bookOrderService;

    @RequestMapping(value = "/")
    public BookOrder getBookOrder(@RequestBody Diamond diamond){
        return bookOrderService.getBookOrder(diamond);
    }
}
