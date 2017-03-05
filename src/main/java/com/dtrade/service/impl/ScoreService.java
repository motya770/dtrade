package com.dtrade.service.impl;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IQuotesService;
import com.dtrade.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IQuotesService quotesService;

    @Override
    public void calculateCategory(Diamond diamond) {

        BigDecimal score = diamond.getScore();

        int realScore = (int) score.doubleValue();

        int step = realScore / 5;

        int lowerBound  = step * 5;
        int upperBound = lowerBound + 5;

        OptionalDouble categoryAvg = diamondService.getDiamondsByScoreBounds(lowerBound, upperBound)
                .stream().mapToDouble(d -> d.getScore().doubleValue()).average();

        quotesService.createCategoryQuote(diamond.getScore(), new BigDecimal(categoryAvg.getAsDouble()));
    }

    @Override
    public BigDecimal calculateScore(Diamond diamond) {
        //TODO write formula for scoring

        //DiamondType diamondType = diamond.getDiamondType();

        BigDecimal carats = diamond.getCarats();

        BigDecimal clarity = diamond.getClarity();

        return new BigDecimal(carats.multiply(clarity).doubleValue() % 100);
    }
}