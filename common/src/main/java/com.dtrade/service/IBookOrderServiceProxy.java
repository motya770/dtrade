package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.utils.MyPair;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;

public interface IBookOrderServiceProxy {

    boolean addNew(TradeOrder tradeOrder);

    boolean remove(TradeOrder tradeOrder);

    Pair<Diamond, Pair<BigDecimal, BigDecimal>>  getSpread(Diamond diamond);

    List<MyPair<Diamond, MyPair<?, ?>>> getSpreadForDiamonds(List<Long> diamonds);

    BookOrderView getBookOrderView(Long diamondId);
}
