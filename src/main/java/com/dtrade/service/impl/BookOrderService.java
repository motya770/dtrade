package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.ITradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    @Autowired
    private ITradeOrderService tradeOrderService;

    //THINK about equals for diamond
    private ConcurrentHashMap<Long, BookOrder> bookOrders = new ConcurrentHashMap<>();

    @Override
    public BookOrder getBookOrder(Diamond diamond) {
        return bookOrders.get(diamond.getId());
    }

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
            logger.debug("adding buy " + order.getId());
            bookOrder.getBuyOrders().add(order);

        }else if(TradeOrderType.SELL.equals(order.getTradeOrderType())){
            logger.debug("adding sell " + order.getId());
            bookOrder.getSellOrders().add(order);
        }else{
            throw new TradeException("Type of the order is not defined!");
        }

        //bookOrders.put(order.getDiamond(), bookOrder);

        bookOrders.put(order.getDiamond().getId(), bookOrder);
    }

    @Override
    public Pair<TradeOrder, TradeOrder> findClosest(Long diamondId) {
        Pair<TradeOrder, TradeOrder> pair = null;

        if(diamondId==null){
            throw new TradeException("Diamond is null!");
        }

        BookOrder bookOrder  = bookOrders.get(diamondId);

        if(bookOrder ==null || bookOrder.getBuyOrders()==null || bookOrder.getSellOrders()==null){
            return null;
        }

        if(bookOrder.getBuyOrders().size()==0){
            return null;

        }

        if(bookOrder.getSellOrders().size()==0){
            return null;
        }

        TradeOrder buyOrder = bookOrder.getBuyOrders().last();
        TradeOrder sellOrder = bookOrder.getSellOrders().first();

        if(buyOrder == null || sellOrder == null){
            return null;
        }

        return Pair.of(buyOrder, sellOrder);
    }

    @Override
    public void remove(TradeOrder order){
        logger.debug("remove D");
        BookOrder book = bookOrders.get(order.getDiamond().getId());
        Optional.ofNullable(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderType().equals(TradeOrderType.BUY)){
                 bookOrder.getBuyOrders().remove(order);
            }else if(order.getTradeOrderType().equals(TradeOrderType.SELL)){
                bookOrder.getSellOrders().remove(order);
            }
        });
    }

    @Override
    public void update(TradeOrder order) {
        logger.debug("update D");
        BookOrder book = bookOrders.get(order.getDiamond().getId());
        Optional.ofNullable(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderType().equals(TradeOrderType.BUY)){
                bookOrder.getBuyOrders().remove(order);
                bookOrder.getBuyOrders().add(order);
            }else if(order.getTradeOrderType().equals(TradeOrderType.SELL)){
                bookOrder.getSellOrders().remove(order);
                bookOrder.getSellOrders().add(order);
            }
        });
    }

    @EventListener(ContextRefreshedEvent.class)
    public void init() {

        logger.debug("ContextRefreshedEvent!!!");

        List<TradeOrder> orders = tradeOrderService.getLiveTradeOrders();
        for(TradeOrder order: orders){
            addNew(order);
        }
    }
}
