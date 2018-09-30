package com.dtrade.service;

import com.dtrade.model.categorytick.CategoryTick;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 3/5/17.
 */
public interface ICategoryTickService {

    CategoryTick createCategoryQuote(Integer score, BigDecimal avarage);

    List<CategoryTick> getByScore(Integer score);

}
