package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kudelin on 7/10/17.
 */
public interface IBookOrderService {

     ConcurrentHashMap<Long, BookOrder> getBookOrders();

     void addNew(TradeOrder order);

     void remove(TradeOrder order);

     void update(TradeOrder order);

     Pair<TradeOrder, TradeOrder> findClosest(Long diamondId);
}
