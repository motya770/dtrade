package com.dtrade.service;

import com.dtrade.model.categorytick.CategoryTick;
import com.dtrade.model.quote.Quote;

import java.math.BigDecimal;

/**
 * Created by kudelin on 3/5/17.
 */
public interface ICategoryTickService {

    CategoryTick createCategoryQuote(Integer score, BigDecimal avarage);

}
