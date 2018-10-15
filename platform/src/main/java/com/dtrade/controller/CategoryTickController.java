package com.dtrade.controller;

import com.dtrade.model.categorytick.CategoryTick;
import com.dtrade.service.ICategoryTickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kudelin on 3/6/17.
 */
@RestController
@RequestMapping(value = "/category-tick")
public class CategoryTickController {

    @Autowired
    private ICategoryTickService categoryTickService;

    @RequestMapping(value = "/for-score")
    public List<CategoryTick> getCategoryTickForScore(@RequestParam Integer score){
        return categoryTickService.getByScore(score);
    }
}
