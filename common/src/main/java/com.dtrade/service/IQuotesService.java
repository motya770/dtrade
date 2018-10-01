package com.dtrade.service;


import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.tradeorder.TradeOrder;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by matvei on 1/3/15.
 */
public interface IQuotesService {

    Quote issueQuote(Pair<TradeOrder, TradeOrder> pair);

    Quote create(Quote quote);

    Quote create(Diamond diamond, BigDecimal price, Long time);

    Quote create(Diamond diamond, BigDecimal ask, BigDecimal bid, Long time);

    Page<Quote> getPagedQuotes(Integer pageNumber, Integer pageSize, Sort sorting) throws TradeException;

    String getRangeQuotes(Diamond diamond, Long start, Long end) throws TradeException;

    List<Pair<?, ?>>  getLastQuoteForDiamonds(List<Diamond> diamonds);

    Quote getLastQuote(Diamond diamond);

}
