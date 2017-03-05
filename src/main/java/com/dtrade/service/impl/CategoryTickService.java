package com.dtrade.service.impl;

import com.dtrade.model.categorytick.CategoryTick;
import com.dtrade.repository.categorytick.CategoryTickRepository;
import com.dtrade.service.ICategoryTickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by kudelin on 3/5/17.
 */

@Service
public class CategoryTickService implements ICategoryTickService {

    @Autowired
    private CategoryTickRepository categoryTickRepository;

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
