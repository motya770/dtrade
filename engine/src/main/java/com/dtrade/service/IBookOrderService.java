package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kudelin on 7/10/17.
 */
public interface IBookOrderService {

     BookOrderView getBookOrderView(Long diamondId);

     ConcurrentHashMap<Long, BookOrder> getBookOrders();

     BookOrder getBookOrder(Long diamondId);

     void addNew(TradeOrder order);

     void remove(TradeOrder order);

     void update(TradeOrder order);

     Pair<TradeOrder, TradeOrder> findClosest(Long diamondId);

     Pair<TradeOrder, List<TradeOrder>> find10Closest(Long diamondId);

     List<Pair<?, ?>> getSpreadForDiamonds(List<Long> diamonds);

     Pair<Diamond, Pair<BigDecimal, BigDecimal>>  getSpread(Diamond diamond);
}
