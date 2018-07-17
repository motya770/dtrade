package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by kudelin on 7/10/17.
 */
@Transactional
@Service
public class BookOrderService implements IBookOrderService {


    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private DiamondService diamondService;

    //THINK about equals for diamond
    private ConcurrentHashMap<Long, BookOrder> bookOrders = new ConcurrentHashMap<>();

    @Override
    public BookOrder getBookOrder(Long diamondId) {
        return bookOrders.get(diamondId);
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

        if(TradeOrderDirection.BUY.equals(order.getTradeOrderDirection())){
            logger.debug("adding buy " + order.getId());
            bookOrder.getBuyOrders().add(order);

        }else if(TradeOrderDirection.SELL.equals(order.getTradeOrderDirection())){
            logger.debug("adding sell " + order.getId());
            bookOrder.getSellOrders().add(order);
        }else{
            throw new TradeException("Type of the order is not defined!");
        }

        //bookOrders.put(order.getDiamond(), bookOrder);

        bookOrders.put(order.getDiamond().getId(), bookOrder);
    }

    @Transactional
    @Override
    public Pair<Diamond, Pair<BigDecimal, BigDecimal>> getSpread(Diamond diamond) {
            Pair<TradeOrder, TradeOrder> closest = this.findClosest(diamond.getId());
            if(closest!=null) {
                return Pair.of(diamond, Pair.of(closest.getFirst().getPrice(), closest.getSecond().getPrice()));
            }
        return null;
    }



    @Transactional
    @Override
    public List<Pair<?, ?>> getSpreadForDiamonds(List<Long> diamonds) {
        List<Pair<?, ?>> response = new ArrayList<>();
        for(Long id : diamonds){
            Diamond diamond = diamondService.find(id);
            response.add(getSpread(diamond));
        }
        return response;
    }


    private BookOrder checkDiamondInBook(Long diamondId){
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

        return bookOrder;
    }

    @Override
    public List<Pair<TradeOrder, TradeOrder>> find10Closest(Long diamondId) {

        BookOrder bookOrder = checkDiamondInBook(diamondId);
        if(bookOrder==null){
            return null;
        }

        List<TradeOrder> buyOrders = bookOrder.getBuyOrders().stream().limit(10).collect(Collectors.toList());
        List<TradeOrder>  sellOrders= bookOrder.getSellOrders().stream().limit(10).collect(Collectors.toList());

        if(buyOrders == null || sellOrders == null){
            return null;
        }

        int maxSize = buyOrders.size() > sellOrders.size()  ? sellOrders.size() : buyOrders.size();

        List<Pair<TradeOrder, TradeOrder>> result = new ArrayList<>();
        for(int i = 0; i < maxSize; i++){
            result.add(Pair.of(buyOrders.get(i), sellOrders.get(i)));
        }

        return result;
    }

    @Override
    public Pair<TradeOrder, TradeOrder> findClosest(Long diamondId) {

        BookOrder bookOrder = checkDiamondInBook(diamondId);
        if(bookOrder==null){
            return null;
        }

        TradeOrder buyOrder = bookOrder.getBuyOrders().first();
        TradeOrder sellOrder = bookOrder.getSellOrders().first();

        if(buyOrder == null || sellOrder == null){
            return null;
        }

        return Pair.of(buyOrder, sellOrder);
    }

    @Override
    public void remove(TradeOrder order){

        BookOrder book = bookOrders.get(order.getDiamond().getId());

        logger.debug("remove D");
        if(book!=null) {
            logger.debug("BUY SIZE {}", book.getBuyOrders().size());
            logger.debug("SELL SIZE {}", book.getSellOrders().size());
        }

        Optional.ofNullable(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderDirection().equals(TradeOrderDirection.BUY)){
                 bookOrder.getBuyOrders().remove(order);
            }else if(order.getTradeOrderDirection().equals(TradeOrderDirection.SELL)){
                bookOrder.getSellOrders().remove(order);
            }
        });
    }

    @Override
    public void update(TradeOrder order) {
        logger.info("update D");
        BookOrder book = bookOrders.get(order.getDiamond().getId());
        Optional.ofNullable(book).ifPresent((bookOrder)->{
            if(order.getTradeOrderDirection().equals(TradeOrderDirection.BUY)){
                bookOrder.getBuyOrders().remove(order);
                bookOrder.getBuyOrders().add(order);
            }else if(order.getTradeOrderDirection().equals(TradeOrderDirection.SELL)){
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

        logger.info("BookOrder loaded {} trade orders", orders.size());
    }
}
