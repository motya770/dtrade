package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.repository.quote.QuoteRepository;
import com.dtrade.service.IQuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Created by matvei on 1/3/15.
 */
@Service
@Transactional(value = "transactionManager")
public class QuotesService implements IQuotesService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Override
    public void create(Quote quote) {
        quoteRepository.save(quote);
    }

    @Override
    public void create(Diamond diamond, BigDecimal value, Long time) {
        Quote quote = new Quote();
        quote.setValue(value);
        quote.setTime(time);
        quote.setDiamond(diamond);
        create(quote);
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
        return null;
    }
}
