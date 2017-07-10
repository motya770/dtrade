package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kudelin on 7/10/17.
 */
@Service
public class BookOrderService implements IBookOrderService {


    @Autowired
    private ITradeOrderService tradeOrderService;

    private ConcurrentHashMap<Diamond, BookOrder> bookOrders = new ConcurrentHashMap<>();

    public void addNew(TradeOrder order, boolean sort){
        BookOrder bookOrder = bookOrders.get(order.getDiamond());
        if(bookOrder==null){
            bookOrder = new BookOrder();
        }

        if(TradeOrderType.BUY.equals(order.getTradeOrderType())){
            bookOrder.getBuy().add(order);

            if(sort){
                sortBuy(bookOrder.getBuy());
            }

        }else if(TradeOrderType.SELL.equals(order.getTradeOrderType())){
            bookOrder.getSell().add(order);

            if(sort){
                sortSell(bookOrder.getSell());
            }

        }else{
            throw new TradeException("Type of the order is not defined!");
        }


        bookOrders.putIfAbsent(order.getDiamond(), bookOrder);
    }

    public void remove(TradeOrder order){
        BookOrder book = bookOrders.get(order.getDiamond());
        Optional.of(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderType().equals(TradeOrderType.BUY)){
                 bookOrder.getBuy().remove(order);
            }else if(order.getTradeOrderType().equals(TradeOrderType.SELL)){
                bookOrder.getSell().remove(order);
            }
        });
    }

    @PostConstruct
    private void init(){

        List<TradeOrder> orders = tradeOrderService.getLiveTradeOrders();
        for(TradeOrder order: orders){
            addNew(order, false);
        }

        bookOrders.forEach((k, v)->{
            sortSell(v.getSell());
            sortBuy(v.getBuy());
        });

    }

    private void sortSell(List<TradeOrder> orders){
        orders.sort((o1, o2) -> {
            return o1.getPrice().compareTo(o2.getPrice());
        });
    }

    private void sortBuy(List<TradeOrder> orders){
        orders.sort((o1, o2)->{
            return o1.getPrice().compareTo(o2.getPrice())*(-1);
        });
    }

    @Override
    public ConcurrentHashMap<Diamond, BookOrder> getInitialBookOrders() {
        return bookOrders;
    }
}
