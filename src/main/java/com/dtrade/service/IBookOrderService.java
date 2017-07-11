package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kudelin on 7/10/17.
 */
public interface IBookOrderService {

     ConcurrentHashMap<Diamond, BookOrder> getBookOrders();

     void addNew(TradeOrder order);

     void remove(TradeOrder order);

     Pair<TradeOrder, TradeOrder> findClosest(Diamond diamond);
}
