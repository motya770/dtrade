package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.depth.DepthQuote;
import org.springframework.data.util.Pair;

import java.util.List;

public interface IDepthQuotesService {

     Pair<List<DepthQuote>, List<DepthQuote>> getDepthQuotes(Diamond diamond);
}
