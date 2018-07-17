package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.bookorder.BookOrder;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.quote.QuoteType;
import com.dtrade.model.quote.depth.DepthQuote;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.quote.QuoteRepository;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.IQuotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Created by matvei on 1/3/15.
 */
@Service
@Transactional
public class QuotesService implements IQuotesService {

    private static long max_history =  2 *  30 *  24 *  60 * 60 * 1_000;

    private static final Logger logger = LoggerFactory.getLogger(QuotesService.class);

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private IBookOrderService bookOrderService;

    @Override
    public Pair<List<DepthQuote>, List<DepthQuote>> getDepthQuotes(Diamond diamond) {

        BookOrder bookOrder = bookOrderService.getBookOrder(diamond.getId());
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


    @Override
    public Quote issueQuote(Pair<TradeOrder, TradeOrder> pair){
     //  long start = System.currentTimeMillis();

        if(pair==null){
            return null;
        }

        TradeOrder buyOrder = pair.getFirst();
        TradeOrder sellOrder = pair.getSecond();
        if(sellOrder == null && buyOrder == null){
            return null;
        }

        BigDecimal bid = buyOrder != null ? buyOrder.getPrice() : null;
        BigDecimal ask = sellOrder != null ? sellOrder.getPrice(): null;

        TradeOrder order = Optional.of(buyOrder).orElse(sellOrder);


        Quote quote = new Quote();
        quote.setAsk(ask);
        quote.setBid(bid);
        quote.setTime(System.currentTimeMillis());
        quote.setDiamond(order.getDiamond());
        quote.setQuoteType(QuoteType.ACTION_QUOTE);

        if(ask!=null && bid != null) {
            BigDecimal avg = ask.add(bid).divide(new BigDecimal("2.0"));
            logger.debug("QUOTE AVG: {}", avg);
            quote.setAvg(avg);
        }

        logger.debug("QUOTE:  {} {}", bid, ask);

        Quote quote1 =  create(quote);
        //System.out.println("QUOTE ISSUING: " + (System.currentTimeMillis() - start));
        return quote1;
    }

    @Override
    public Quote create(Quote quote) {
         return quoteRepository.save(quote);
    }

    @Override
    public Quote create(Diamond diamond, BigDecimal ask, BigDecimal bid, Long time) {
        Quote quote = new Quote();
        quote.setAsk(ask);
        quote.setBid(bid);
        quote.setTime(time);
        quote.setDiamond(diamond);
        quote.setQuoteType(QuoteType.SCORE_QUOTE);
        return create(quote);
    }

    @Override
    public Quote create(Diamond diamond, BigDecimal price,  Long time) {
        Quote quote = new Quote();
        quote.setPrice(price);
        quote.setTime(time);
        quote.setDiamond(diamond);
        quote.setQuoteType(QuoteType.ACTION_QUOTE);
        return create(quote);
    }

    @Override
    public List<Pair<?, ?>>  getLastQuoteForDiamonds(List<Diamond> diamonds) {
        List<Pair<?, ?>> responce = new ArrayList<>();
        for(Diamond diamond : diamonds){
            Quote quote = quoteRepository.findFirstByDiamondOrderByTimeDesc(diamond);
            if(quote!=null) {
                Pair<?, ?> pair = Pair.of(diamond, quote);
                responce.add(pair);
            }
        }
        return responce;
    }

    @Override
    public Quote getLastQuote(Diamond diamond) {
        return quoteRepository.findFirstByDiamondOrderByTimeDesc(diamond);
    }

    @Override
    public Page<Quote> getPagedQuotes(Integer pageNumber, Integer pageSize, Sort sorting) throws TradeException {
        if(sorting == null) {
            return quoteRepository.findAll(new PageRequest(pageNumber, pageSize));
        }else{
            return quoteRepository.findAll(new PageRequest(pageNumber, pageSize, sorting));
        }
    }

    @Override
    public String getRangeQuotes(Diamond diamond, Long start, Long end) throws TradeException {
        if(end==null){
            end = System.currentTimeMillis();
        }

        int pageSize = 100;

        if(start==null){
            start = System.currentTimeMillis() - Duration.ofDays(100).toMillis();
        }else{
            //for the purpose of reducing load for front
            pageSize = 2;
        }

        if(diamond==null){
            return null;
        }
        //TODO potential bug


        List<Quote> quotes = quoteRepository.getRangeQuotes(diamond.getId(), start, end, QuoteType.ACTION_QUOTE, new PageRequest(0, pageSize));
        //QuoteDTO[] quoteDTOS = new QuoteDTO[quotes.size()];

        if(quotes.size()==0){
            return "[]";
        }

        //sorry hightcharts convention

        StringBuilder builder = new StringBuilder();
        builder.append("[");

        for (int i = (quotes.size() - 1); i >= 0; i--){

            Quote quote = quotes.get(i);
            builder.append("[");
            builder.append(quote.getTime());
            builder.append(",");
            if(quote.getAvg()!=null) {
                builder.append(quote.getAvg());
            }else {
                builder.append(quote.getAsk().add(quote.getBid()).divide(new BigDecimal("2.0")));
            }
            builder.append("]");
            if(i > 0){
                builder.append(",");
            }
//            QuoteDTO dto = new QuoteDTO();
//            dto.setAvg(quote.getAvg());
//            dto.setTime(quote.getTime());
            //quoteDTOS[quotes.size() - 1 - i]= dto;
        }

        builder.append("]");

        return builder.toString();
    }
}
