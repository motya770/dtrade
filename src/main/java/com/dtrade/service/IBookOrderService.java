package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kudelin on 7/10/17.
 */
public interface IBookOrderService {

     ConcurrentHashMap<Long, BookOrder> getBookOrders();

     BookOrder getBookOrder(Diamond diamond);

     void addNew(TradeOrder order);

     void remove(TradeOrder order);

     void update(TradeOrder order);

     Pair<TradeOrder, TradeOrder> findClosest(Long diamondId);

     List<Pair<TradeOrder, TradeOrder>> find10Closest(Long diamondId);

     List<Pair<?, ?>> getSpreadForDiamonds(List<Diamond> diamonds);
}
