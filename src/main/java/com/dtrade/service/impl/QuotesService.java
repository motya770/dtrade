package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.quote.QuoteType;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.quote.QuoteRepository;
import com.dtrade.service.IQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


/**
 * Created by matvei on 1/3/15.
 */
@Service
@Transactional
public class QuotesService implements IQuotesService {

    private static long max_history =  2 *  30 *  24 *  60 * 60 * 1_000;

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public Quote issueQuote(Pair<TradeOrder, TradeOrder> pair){

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
        return create(quote);
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
    public Page<Quote> getPagedQuotes(Integer pageNumber, Integer pageSize, Sort sorting) throws TradeException {
        if(sorting == null) {
            return quoteRepository.findAll(new PageRequest(pageNumber, pageSize));
        }else{
            return quoteRepository.findAll(new PageRequest(pageNumber, pageSize, sorting));
        }
    }

    @Override
    public List<Quote> getRangeQuotes(Diamond diamond, Long start, Long end) throws TradeException {
        if(end==null){
            end = System.currentTimeMillis();
        }
        if(start==null){
            start = end - max_history;
        }
        return quoteRepository.getRangeQuotes(diamond.getId(), start, end, QuoteType.ACTION_QUOTE, new PageRequest(0, 100));
    }
}
