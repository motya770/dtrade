package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrdersHolder;
import com.dtrade.model.diamond.Diamond;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kudelin on 7/10/17.
 */
public interface IBookOrderService {

    ConcurrentHashMap<Diamond, BookOrder> getInitialBookOrders();

}
