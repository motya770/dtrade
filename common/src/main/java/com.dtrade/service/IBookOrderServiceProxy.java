package com.dtrade.service;

import com.dtrade.model.bookorder.BookOrderView;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.SimpleQuote;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.util.Pair;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface IBookOrderServiceProxy {

    Mono<Boolean> addNew(TradeOrder tradeOrder);

    Boolean remove(TradeOrder tradeOrder);

    Mono<SimpleQuote> getSpread(Diamond diamond);

    Mono<String> getSpreadForDiamonds(List<Long> diamonds);

    Mono<BookOrderView> getBookOrderView(Long diamondId);
}
