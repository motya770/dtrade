package com.dtrade.service.impl;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderServiceProxy;
import com.dtrade.service.IRabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookOrderServiceProxy implements IBookOrderServiceProxy {

    @Autowired
    private IRabbitService rabbitService;

    @Override
    public List<Pair<?, ?>> getSpreadForDiamonds(List<Long> diamonds) {
        return null;
    }

    @Override
    public BookOrderView getBookOrderView(Long diamondId) {
        return null;
    }

    @Override
    public Pair<Diamond, Pair<BigDecimal, BigDecimal>> getSpread(Diamond diamond) {
        return null;
    }

    @Override
    public boolean remove(TradeOrder tradeOrder) {
        return false;
    }

    @Override
    public BookOrder getBookOrder(Long diamondId) {
        return null;
    }

    @Override
    public boolean addNew(TradeOrder tradeOrder) {
        return false;
    }
}
