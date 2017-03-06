package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.ICategoryTickService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.OptionalDouble;

/**
 * Created by kudelin on 3/4/17.
 */
@Service
public class ScoreService implements IScoreService {


    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private ICategoryTickService categoryTickService;

    @Override
    public Pair<Integer, Integer> calculateScoreBounds(Integer score) {

        int realScore = 0;
        if(score != null){
            realScore = score;
        }

        int step = realScore / 5;

        int lowerBound  = step * 5;
        int upperBound = lowerBound + 5;

        if(lowerBound >= upperBound){
            throw new TradeException("Lower score bound is " + lowerBound  + " and upper bound is " + upperBound);
        }

        return Pair.of(lowerBound, upperBound);
    }


    @Override
    public void calculateCategory(Diamond diamond) {

        Integer score = diamond.getScore();
        Pair<Integer, Integer> bounds = calculateScoreBounds(score);
//        int realScore = 0;
//        if(score != null){
//            realScore = score;
//        }
//
//        int step = realScore / 5;
//
//        int lowerBound  = step * 5;
//        int upperBound = lowerBound + 5;

        OptionalDouble categoryAvg = diamondService.getDiamondsByScoreBounds(bounds.getFirst(), bounds.getSecond())
                .stream().mapToDouble(d -> d.getScore().doubleValue()).average();
        double categoryAvgScore = categoryAvg.orElseGet(()->{
            double d = 0.0;
            return d;
        });

        //TODO fix new BigDecimal and check calculations
        categoryTickService.createCategoryQuote(diamond.getScore(), new BigDecimal(categoryAvgScore));
    }

    @Override
    public Integer calculateScore(Diamond diamond) {
        //TODO write formula for scoring

        //DiamondType diamondType = diamond.getDiamondType();

        BigDecimal carats = diamond.getCarats();

        BigDecimal clarity = diamond.getClarity();

        int result = (int) carats.multiply(clarity).doubleValue() % 100;
        return result;
    }
}