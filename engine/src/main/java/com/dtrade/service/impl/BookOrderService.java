package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.SimpleQuote;
import com.dtrade.model.tradeorder.TradeEngineState;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.ITradeOrderService;
import com.dtrade.service.core.ITradeEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by kudelin on 7/10/17.
 */
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
@Service
public class BookOrderService implements IBookOrderService {


    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private DiamondService diamondService;

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    //THINK about equals for diamond
    private ConcurrentHashMap<Long, BookOrder> bookOrders = new ConcurrentHashMap<>();

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init(){
        executorService.scheduleAtFixedRate(()->cleanBookOrders(), 10, 5, TimeUnit.MINUTES);
    }

    private void cleanList(ConcurrentSkipListSet<TradeOrder> list){
         int size = list.size();
         int counter = size - 500;
         if(counter > 0){
             for(int i = 0; i<counter; i++){
                 list.pollLast();
             }
         }
    }

    private void  cleanBookOrders(){
        bookOrders.forEach((k, v)->{
            cleanList(v.getSellOrders());
            cleanList(v.getBuyOrders());
        });
    }

    @Override
    public BookOrder getBookOrder(Long diamondId) {
        return bookOrders.get(diamondId);

//        BookOrder bookOrder =  new BookOrder();
//        bookOrder.setBuyOrders(tradeOrderRepository.getBuyOrders(diamondId));
//        bookOrder.setSellOrders(tradeOrderRepository.getSellOrders(diamondId));
//        return bookOrder;
    }

    @Override
    public ConcurrentHashMap<Long, BookOrder> getBookOrders(){
        return bookOrders;
    }

    @Override
    public BookOrderView getBookOrderView(Long diamondId){
        //long launch = System.currentTimeMillis();
        BookOrder bookOrder = getBookOrder(diamondId);
        List<TradeOrderDTO> buyOrders  = null;
        List<TradeOrderDTO> sellOrders  = null;

        if(bookOrder==null){
            return null;
        }
        if(bookOrder.getBuyOrders()!=null) {
            buyOrders = bookOrder.getBuyOrders().stream().limit(10)
                    .map(tradeOrder -> tradeOrderService.convert(tradeOrder)).collect(Collectors.toList());
        }

        if(bookOrder.getSellOrders()!=null) {
            sellOrders = bookOrder.getSellOrders().stream().limit(10)
                    .map(tradeOrder -> tradeOrderService.convert(tradeOrder)).collect(Collectors.toList());
            Collections.reverse(sellOrders);
        }

        //System.out.println("BookOrder: " + (System.currentTimeMillis() - launch));
        return new BookOrderView(buyOrders, sellOrders);
    }

    @Override
    public void addNew(TradeOrder order, boolean initialize){

        order = tradeOrderRepository.findById(order.getId()).get();

        BookOrder bookOrder = bookOrders.get(order.getDiamond().getId());
        boolean newBookOrder = false;
        if(bookOrder==null){
            bookOrder = new BookOrder();
            newBookOrder = true;
        }

        if(TradeOrderDirection.BUY.equals(order.getTradeOrderDirection())){
            //logger.debug("adding buy " + order.getId());
            bookOrder.getBuyOrders().add(order);
        }else if(TradeOrderDirection.SELL.equals(order.getTradeOrderDirection())){
            //logger.debug("adding sell " + order.getId());
            bookOrder.getSellOrders().add(order);
        }else{
            throw new TradeException("Type of the order is not defined!");
        }

        if(newBookOrder) {
            bookOrders.put(order.getDiamond().getId(), bookOrder);
        }
    }

    @Transactional
    @Override
    public SimpleQuote getSpread(Diamond diamond) {
            Pair<TradeOrder, TradeOrder> closest = this.findClosest(diamond.getId());
            if(closest!=null) {
                SimpleQuote simpleQuote = new SimpleQuote();
                simpleQuote.setDiamond(diamond);
                simpleQuote.setBid(closest.getFirst().getPrice());
                simpleQuote.setAsk(closest.getSecond().getPrice());
                return simpleQuote;
            }
        return null;
    }

    @Transactional
    @Override
    public List<SimpleQuote> getSpreadForDiamonds(List<Long> diamonds) {
        List<SimpleQuote> response = new ArrayList<>();
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

        BookOrder bookOrder  = getBookOrder(diamondId); //bookOrders.get(diamondId);

        if(bookOrder ==null || bookOrder.getBuyOrders()==null || bookOrder.getSellOrders()==null){
            return null;
        }

        if(bookOrder.getBuyOrders().isEmpty()){
            return null;

        }

        if(bookOrder.getSellOrders().isEmpty()){
            return null;
        }

        return bookOrder;
    }

    @Override
    public Pair<TradeOrder, List<TradeOrder>>  find10Closest(Long diamondId) {

        BookOrder bookOrder = checkDiamondInBook(diamondId);
        if(bookOrder==null){
            return null;
        }

        TradeOrder  buyOrder = bookOrder.getBuyOrders().stream().findFirst().orElse(null);
        List<TradeOrder> sellOrder = bookOrder.getSellOrders().stream().limit(10).collect(Collectors.toList());


        /*
        bookOrder.getBuyOrders().stream().limit(10).forEach(tradeOrder -> {
                    System.out.println("Buy: " + tradeOrder.getId() + " " + tradeOrder.getTraderOrderStatus());
                    System.out.println(tradeOrderRepository.findById(tradeOrder.getId()).get().getTraderOrderStatus());
        });

        bookOrder.getSellOrders().stream().limit(10).forEach(tradeOrder -> {
            System.out.print("Sell: " + tradeOrder.getId() + " " + tradeOrder.getTraderOrderStatus() + " ");
            tradeOrderRepository.findById(tradeOrder.getId()).ifPresent(tradeOrder1 -> {
                        System.out.println(tradeOrder1.getTraderOrderStatus());
                    }
            );
        });*/

        if(buyOrder == null || sellOrder == null || sellOrder.size()==0){
            return null;
        }

        return Pair.of(buyOrder, sellOrder);

        /*
        int maxSize = buyOrders.size() > sellOrders.size()  ? sellOrders.size() : buyOrders.size();

        List<Pair<TradeOrder, TradeOrder>> result = new ArrayList<>();
        for(int i = 0; i < maxSize; i++){
            result.add(Pair.of(buyOrders.get(i), sellOrders.get(i)));
        }

        return result;*/
    }


    public List<Pair<TradeOrder, TradeOrder>> findClosestList(Long diamondId) {

        BookOrder bookOrder = checkDiamondInBook(diamondId);
        if(bookOrder==null){
            return null;
        }

        List<TradeOrder> buyOrders = bookOrder.getBuyOrders().stream().filter(tradeOrder -> tradeOrder.getTradeEngineState()==null).limit(10).collect(Collectors.toList());
        List<TradeOrder> sellOrders = bookOrder.getSellOrders().stream().filter(tradeOrder -> tradeOrder.getTradeEngineState()==null).limit(10).collect(Collectors.toList());

        int maxSize = buyOrders.size() > sellOrders.size() ? sellOrders.size() : buyOrders.size();

        List<Pair<TradeOrder, TradeOrder>> pairs = new ArrayList<>();
        for(int i=0; i<maxSize; i++){
            TradeOrder buy = buyOrders.get(i);
            TradeOrder sell = sellOrders.get(i);
            pairs.add(Pair.of(buy, sell));
        }
        return pairs;
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
    public boolean remove(TradeOrder order){

        BookOrder book = bookOrders.get(order.getDiamond().getId());

        //System.out.println("remove D 1.0 : " + order.getId() + " " + Thread.currentThread().getName() + " " + LocalTime.now());
        if(book!=null) {
            //System.out.println("BUY SIZE {}"  +  book.getBuyOrders().size());
           // System.out.println("SELL SIZE {}" +  book.getSellOrders().size());
        }

        logger.debug("remove D 1.1 : " + order.getId() + " " + Thread.currentThread().getName() + " " + LocalTime.now());

        Optional.ofNullable(book).map(bookOrder -> {
            if(order.getTradeOrderDirection().equals(TradeOrderDirection.BUY)){
                logger.debug("remove D 1.3 : " + order.getId() + " " + Thread.currentThread().getName() + " " + LocalTime.now());

                 boolean res = bookOrder.getBuyOrders().remove(order);
                 if(res){
                     bookOrder.getBuyCount().decrementAndGet();
                 }
                 return res;
            }else if(order.getTradeOrderDirection().equals(TradeOrderDirection.SELL)){
                logger.debug("remove D 1.4 : " + order.getId() + " " + order.getPrice() +  " " + Thread.currentThread().getName() + " " + LocalTime.now());

                boolean res = bookOrder.getSellOrders().remove(order);
                if(res) {
                    bookOrder.getSellCount().decrementAndGet();
                }
            }
            return false;
        }).orElse(Boolean.FALSE);
        return false;
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
}
