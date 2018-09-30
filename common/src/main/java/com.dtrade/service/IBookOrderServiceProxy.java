package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;

public interface IBookOrderServiceProxy {

    boolean addNew(TradeOrder tradeOrder);

    boolean remove(TradeOrder tradeOrder);

    BookOrder getBookOrder(Long diamondId);

    Pair<Diamond, Pair<BigDecimal, BigDecimal>>  getSpread(Diamond diamond);

    List<Pair<?, ?>> getSpreadForDiamonds(List<Long> diamonds);

    BookOrderView getBookOrderView(Long diamondId);
}
