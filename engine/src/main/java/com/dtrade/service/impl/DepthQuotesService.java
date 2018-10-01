package com.dtrade.service.impl;

import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.depth.DepthQuote;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.IDepthQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class DepthQuotesService implements IDepthQuotesService {

    @Autowired
    private IBookOrderService bookOrderService;

    @Override
    public Pair<List<DepthQuote>, List<DepthQuote>> getDepthQuotes(Diamond diamond) {

        BookOrder bookOrder = bookOrderService.getBookOrder(diamond.getId());

        if(bookOrder==null){
            return null;
        }

        List<TradeOrder> buyOrders = bookOrder.getBuyOrders().stream().limit(50).collect(Collectors.toList());
        List<TradeOrder> sellOrders = bookOrder.getSellOrders().stream().limit(50).collect(Collectors.toList());

        //TODO optimize
        Collections.reverse(buyOrders);
        List<DepthQuote> buyQuotes  = buildBuyDepthQuotes(buyOrders);
        List<DepthQuote> sellQuotes  = buildSellDepthQuotes(sellOrders);

        Collections.reverse(buyQuotes);
        return Pair.of(buyQuotes, sellQuotes);
    }

    private List<DepthQuote> buildBuyDepthQuotes(List<TradeOrder> orders){
        // System.out.println("\n\n START");

        DepthQuote depthQuote = new DepthQuote();
        List<DepthQuote> quotes  = new ArrayList<>();
        BigDecimal lastPrice = null;
        BigDecimal amount = BigDecimal.ZERO;
        for(int i = orders.size()-1; i >= 0; i--){

            TradeOrder order = orders.get(i);
            if(i==orders.size()-1){
                depthQuote.setAmount(order.getAmount());
                depthQuote.setPrice(order.getPrice());
                lastPrice = order.getPrice();
            }

            if(lastPrice.compareTo(order.getPrice())!=0){
                quotes.add(depthQuote);

                depthQuote = new DepthQuote();
                amount = amount.add(order.getAmount());
                depthQuote.setAmount(amount);
                depthQuote.setPrice(order.getPrice());
                lastPrice = order.getPrice();

            }else{
                amount = amount.add(order.getAmount());
                depthQuote.setAmount(amount);
            }

            //System.out.println("price: " + order.getPrice() + " am: " + order.getAmount() + " sum: " + amount);

            if(i==0){
                quotes.add(depthQuote);
            }
        }

        //System.out.println("END \n\n\n");
        return quotes;
    }

    private List<DepthQuote> buildSellDepthQuotes(List<TradeOrder> orders){
        // System.out.println("\n\n START");

        DepthQuote depthQuote = new DepthQuote();
        List<DepthQuote> quotes  = new ArrayList<>();
        BigDecimal lastPrice = null;
        BigDecimal amount = BigDecimal.ZERO;
        for(int i = 0; i < orders.size(); i++){

            TradeOrder order = orders.get(i);
            if(i==0){
                depthQuote.setAmount(order.getAmount());
                depthQuote.setPrice(order.getPrice());
                lastPrice = order.getPrice();
            }

            if(lastPrice.compareTo(order.getPrice())!=0){
                quotes.add(depthQuote);

                depthQuote = new DepthQuote();
                amount = amount.add(order.getAmount());
                depthQuote.setAmount(amount);
                depthQuote.setPrice(order.getPrice());
                lastPrice = order.getPrice();

            }else{
                amount = amount.add(order.getAmount());
                depthQuote.setAmount(amount);
            }

            // System.out.println("price: " + order.getPrice() + " am: " + order.getAmount() + " sum: " + amount);

            if(i==(orders.size()-1)){
                quotes.add(depthQuote);
            }
        }

        //System.out.println("END \n\n\n");
        return quotes;
    }

}
