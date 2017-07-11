package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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

    public ConcurrentHashMap<Diamond, BookOrder> getBookOrders(){
        return bookOrders;
    }

    @Override
    public void addNew(TradeOrder order){
        BookOrder bookOrder = bookOrders.get(order.getDiamond());
        if(bookOrder==null){
            bookOrder = new BookOrder();
        }

        if(TradeOrderType.BUY.equals(order.getTradeOrderType())){
            bookOrder.getBuy().add(order);

        }else if(TradeOrderType.SELL.equals(order.getTradeOrderType())){
            bookOrder.getSell().add(order);

        }else{
            throw new TradeException("Type of the order is not defined!");
        }

        bookOrders.putIfAbsent(order.getDiamond(), bookOrder);
    }

    @Override
    public Pair<TradeOrder, TradeOrder> findClosest(Diamond diamond) {
        Pair<TradeOrder, TradeOrder> pair = null;

        if(diamond==null){
            throw new TradeException("Diamond is null!");
        }


        BookOrder bookOrder  = bookOrders.get(diamond);

        if(bookOrder ==null || bookOrder.getBuy()==null || bookOrder.getSell()==null){
            return null;
        }

        TradeOrder buyOrder = bookOrder.getBuy().last();
        TradeOrder sellOrder = bookOrder.getSell().first();

        if(buyOrder == null || sellOrder == null){
            return null;
        }

        return Pair.of(buyOrder, sellOrder);
    }

    @Override
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

    @Override
    public void update(TradeOrder order) {
        BookOrder book = bookOrders.get(order.getDiamond());
        Optional.of(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderType().equals(TradeOrderType.BUY)){
                bookOrder.getBuy().remove(order);
                bookOrder.getBuy().add(order);
            }else if(order.getTradeOrderType().equals(TradeOrderType.SELL)){
                bookOrder.getSell().remove(order);
                bookOrder.getSell().add(order);
            }
        });
    }

    @PostConstruct
    private void init(){
        List<TradeOrder> orders = tradeOrderService.getLiveTradeOrders();
        for(TradeOrder order: orders){
            addNew(order);
        }
    }
}
