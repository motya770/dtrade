package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.depth.DepthQuote;
import org.springframework.data.util.Pair;

import java.util.List;

public interface IQuotesServiceProxy {

    String getDepthQuotes(Diamond diamond);
}
