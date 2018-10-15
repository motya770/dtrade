package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.categorytick.CategoryTick;
import com.dtrade.repository.categorytick.CategoryTickRepository;
import com.dtrade.service.ICategoryTickService;
import com.dtrade.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 3/5/17.
 */

@Service
public class CategoryTickService implements ICategoryTickService {

    @Autowired
    private CategoryTickRepository categoryTickRepository;

    @Autowired
    private IScoreService scoreService;

    @Override
    public List<CategoryTick> getByScore(Integer score) {
        if(score==null){
            throw new TradeException("Score can't be null");
        }

        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(score);
        return categoryTickRepository.getByScoreBounds(bounds.getFirst(), bounds.getSecond());
    }

    @Override
    public CategoryTick createCategoryQuote(Integer score, BigDecimal avarage) {

        CategoryTick categoryTick = new CategoryTick();
        categoryTick.setScore(score);
        categoryTick.setTime(System.currentTimeMillis());
        categoryTick.setAvarage(avarage);

        categoryTick = categoryTickRepository.save(categoryTick);

        return categoryTick;
    }
}
