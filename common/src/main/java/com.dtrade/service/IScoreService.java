package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;
import org.springframework.data.util.Pair;
/**
 * Created by kudelin on 3/4/17.
 */
public interface IScoreService {

    Pair<Integer, Integer> calculateScoreBounds(Integer score);

    void calculateCategory(Diamond diamond);

    Integer calculateScore(Diamond diamond);
}
