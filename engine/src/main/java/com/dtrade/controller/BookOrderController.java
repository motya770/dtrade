package com.dtrade.controller;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/book-order", method = RequestMethod.POST)
public class BookOrderController {

    @Autowired
    private IBookOrderService bookOrderService;

    @RequestMapping(value = "/get-diamonds-spread", method = RequestMethod.POST)
    public List<Pair<?,?>> getDiamondSpread(@RequestBody(required = true) List<Long> diamondIds){
        return bookOrderService.getSpreadForDiamonds(diamondIds);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove(@RequestBody(required = true) TradeOrder tradeOrder){
         bookOrderService.remove(tradeOrder);
    }

    @RequestMapping(value = "/add-new", method = RequestMethod.POST)
    public void addNew(@RequestBody(required = true) TradeOrder tradeOrder){
        bookOrderService.addNew(tradeOrder);
    }

    @RequestMapping(value = "/get-view", method = RequestMethod.POST)
    public BookOrderView addNew(@RequestBody(required = true) Long diamondId){
       return bookOrderService.getBookOrderView(diamondId);
    }

    @RequestMapping(value = "/get-spread", method = RequestMethod.POST)
    public Pair<Diamond, Pair<BigDecimal, BigDecimal>> getSpread(@RequestBody(required = true) Diamond diamond){
        return bookOrderService.getSpread(diamond);
    }
}
