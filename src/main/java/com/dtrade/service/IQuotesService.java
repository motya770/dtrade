package com.dtrade.service;


import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by matvei on 1/3/15.
 */
public interface IQuotesService {


    //void generateQuotes();

    Quote create(Quote quote);

    Quote create(Diamond diamond, BigDecimal ask, BigDecimal bid, Long time);

    Page<Quote> getPagedQuotes(Integer pageNumber, Integer pageSize, Sort sorting) throws TradeException;

    List<Quote> getRangeQuotes(Diamond diamond, Long start, Long end) throws TradeException;

}
