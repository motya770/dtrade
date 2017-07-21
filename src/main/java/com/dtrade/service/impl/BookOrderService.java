package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kudelin on 7/10/17.
 */
@Transactional
@Service
public class BookOrderService implements IBookOrderService {

    @Autowired
    private ITradeOrderService tradeOrderService;

    //THINK about equals for diamond
    private ConcurrentHashMap<Long, BookOrder> bookOrders = new ConcurrentHashMap<>();

    @Override
    public ConcurrentHashMap<Long, BookOrder> getBookOrders(){
        return bookOrders;
    }

    @Override
    public void addNew(TradeOrder order){
        BookOrder bookOrder = bookOrders.get(order.getDiamond().getId());
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

        //bookOrders.put(order.getDiamond(), bookOrder);

        bookOrders.putIfAbsent(order.getDiamond().getId(), bookOrder);
    }

    @Override
    public Pair<TradeOrder, TradeOrder> findClosest(Long diamondId) {
        Pair<TradeOrder, TradeOrder> pair = null;

        if(diamondId==null){
            throw new TradeException("Diamond is null!");
        }

        BookOrder bookOrder  = bookOrders.get(diamondId);

        if(bookOrder ==null || bookOrder.getBuy()==null || bookOrder.getSell()==null){
            return null;
        }

        if(bookOrder.getBuy().size()==0){
            return null;

        }

        if(bookOrder.getSell().size()==0){
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
        BookOrder book = bookOrders.get(order.getDiamond().getId());
        Optional.ofNullable(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderType().equals(TradeOrderType.BUY)){
                 bookOrder.getBuy().remove(order);
            }else if(order.getTradeOrderType().equals(TradeOrderType.SELL)){
                bookOrder.getSell().remove(order);
            }
        });
    }

    @Override
    public void update(TradeOrder order) {
        BookOrder book = bookOrders.get(order.getDiamond().getId());
        Optional.ofNullable(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderType().equals(TradeOrderType.BUY)){
                bookOrder.getBuy().remove(order);
                bookOrder.getBuy().add(order);
            }else if(order.getTradeOrderType().equals(TradeOrderType.SELL)){
                bookOrder.getSell().remove(order);
                bookOrder.getSell().add(order);
            }
        });
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {

        System.out.println("ContextRefreshedEvent!!!");

        List<TradeOrder> orders = tradeOrderService.getLiveTradeOrders();
        for(TradeOrder order: orders){
            addNew(order);
        }
    }
}
