package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;

import java.math.BigDecimal;

/**
 * Created by kudelin on 3/4/17.
 */
public interface IScoreService {


    void calculateCategory(Diamond diamond);

    BigDecimal calculateScore(Diamond diamond);
}
