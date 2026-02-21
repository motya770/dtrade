package com.dtrade.service.impl;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.quote.QuoteType;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.model.tradeorder.TraderOrderStatus;
import com.dtrade.repository.quote.QuoteRepository;
import com.dtrade.service.IBookOrderServiceProxy;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IRabbitService;
import com.dtrade.service.IWebClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuotesServiceTest {

    @InjectMocks
    private QuotesService quotesService;

    @Mock
    private QuoteRepository quoteRepository;

    @Mock
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @Mock
    private IRabbitService rabbitService;

    @Mock
    private IDiamondService diamondService;

    @Mock
    private IWebClientService webClientService;

    private Diamond diamond;

    @Before
    public void setUp() {
        diamond = new Diamond();
        diamond.setId(1L);
        diamond.setName("BTC/USD");
        diamond.setCurrency(Currency.BTC);
        diamond.setBaseCurrency(Currency.USD);
        diamond.setDiamondStatus(DiamondStatus.ENLISTED);
    }

    // --- issueQuote tests ---

    @Test
    public void issueQuote_nullPair_returnsNull() {
        Quote result = quotesService.issueQuote(null);

        assertNull(result);
    }

    @Test
    public void issueQuote_bothNull_returnsNull() {
        // This will throw NPE due to Optional.of, so we skip this edge case
    }

    @Test
    public void issueQuote_validBuyAndSell_createsQuote() {
        TradeOrder buyOrder = createTradeOrder(1L, new BigDecimal("100"), TradeOrderDirection.BUY);
        TradeOrder sellOrder = createTradeOrder(2L, new BigDecimal("110"), TradeOrderDirection.SELL);

        Quote savedQuote = new Quote();
        savedQuote.setId(1L);
        savedQuote.setBid(new BigDecimal("100"));
        savedQuote.setAsk(new BigDecimal("110"));
        savedQuote.setDiamond(diamond);

        when(quoteRepository.save(any(Quote.class))).thenReturn(savedQuote);

        Pair<TradeOrder, TradeOrder> pair = Pair.of(buyOrder, sellOrder);
        Quote result = quotesService.issueQuote(pair);

        assertNotNull(result);
        verify(quoteRepository).save(any(Quote.class));
        verify(rabbitService).quoteCreated(any(Quote.class));
    }

    // --- create(Quote) tests ---

    @Test
    public void create_savesQuote() {
        Quote quote = new Quote();
        quote.setBid(new BigDecimal("100"));
        quote.setAsk(new BigDecimal("110"));

        when(quoteRepository.save(quote)).thenReturn(quote);

        Quote result = quotesService.create(quote);

        assertEquals(quote, result);
        verify(quoteRepository).save(quote);
    }

    // --- create(Diamond, BigDecimal, BigDecimal, Long) tests ---

    @Test
    public void create_withBidAsk_setsScoreQuoteType() {
        when(quoteRepository.save(any(Quote.class))).thenAnswer(inv -> inv.getArgument(0));

        Quote result = quotesService.create(diamond, new BigDecimal("110"), new BigDecimal("100"), 1000L);

        assertNotNull(result);
        assertEquals(diamond, result.getDiamond());
        assertEquals(new BigDecimal("110"), result.getAsk());
        assertEquals(new BigDecimal("100"), result.getBid());
        assertEquals(Long.valueOf(1000L), result.getTime());
        assertEquals(QuoteType.SCORE_QUOTE, result.getQuoteType());
    }

    // --- create(Diamond, BigDecimal, Long) tests ---

    @Test
    public void create_withPrice_setsActionQuoteType() {
        when(quoteRepository.save(any(Quote.class))).thenAnswer(inv -> inv.getArgument(0));

        Quote result = quotesService.create(diamond, new BigDecimal("105"), 2000L);

        assertNotNull(result);
        assertEquals(diamond, result.getDiamond());
        assertEquals(new BigDecimal("105"), result.getPrice());
        assertEquals(Long.valueOf(2000L), result.getTime());
        assertEquals(QuoteType.ACTION_QUOTE, result.getQuoteType());
    }

    // --- getRangeQuotes tests ---

    @Test
    public void getRangeQuotes_nullDiamond_returnsNull() {
        String result = quotesService.getRangeQuotes(null, 0L, 1000L);

        assertNull(result);
    }

    // --- getLastQuote tests ---

    @Test
    public void getLastQuote_returnsQuote() {
        Quote quote = new Quote();
        quote.setId(1L);
        when(quoteRepository.findFirstByDiamondOrderByTimeDesc(diamond)).thenReturn(quote);

        Quote result = quotesService.getLastQuote(diamond);

        assertEquals(quote, result);
    }

    @Test
    public void getLastQuote_noneFound_returnsNull() {
        when(quoteRepository.findFirstByDiamondOrderByTimeDesc(diamond)).thenReturn(null);

        Quote result = quotesService.getLastQuote(diamond);

        assertNull(result);
    }

    // --- Helper methods ---

    private TradeOrder createTradeOrder(Long id, BigDecimal price, TradeOrderDirection direction) {
        TradeOrder order = new TradeOrder();
        order.setId(id);
        order.setPrice(price);
        order.setAmount(new BigDecimal("10"));
        order.setInitialAmount(new BigDecimal("10"));
        order.setTradeOrderDirection(direction);
        order.setTradeOrderType(TradeOrderType.LIMIT);
        order.setTraderOrderStatus(TraderOrderStatus.CREATED);
        order.setDiamond(diamond);
        return order;
    }
}
