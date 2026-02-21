package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.util.Pair;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService;

    @Mock
    private DiamondService diamondService;

    // --- calculateScoreBounds tests ---

    @Test
    public void calculateScoreBounds_zeroScore_returnsBounds0to5() {
        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(0);

        assertEquals(Integer.valueOf(0), bounds.getFirst());
        assertEquals(Integer.valueOf(5), bounds.getSecond());
    }

    @Test
    public void calculateScoreBounds_nullScore_returnsBounds0to5() {
        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(null);

        assertEquals(Integer.valueOf(0), bounds.getFirst());
        assertEquals(Integer.valueOf(5), bounds.getSecond());
    }

    @Test
    public void calculateScoreBounds_score3_returnsBounds0to5() {
        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(3);

        assertEquals(Integer.valueOf(0), bounds.getFirst());
        assertEquals(Integer.valueOf(5), bounds.getSecond());
    }

    @Test
    public void calculateScoreBounds_score5_returnsBounds5to10() {
        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(5);

        assertEquals(Integer.valueOf(5), bounds.getFirst());
        assertEquals(Integer.valueOf(10), bounds.getSecond());
    }

    @Test
    public void calculateScoreBounds_score7_returnsBounds5to10() {
        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(7);

        assertEquals(Integer.valueOf(5), bounds.getFirst());
        assertEquals(Integer.valueOf(10), bounds.getSecond());
    }

    @Test
    public void calculateScoreBounds_score10_returnsBounds10to15() {
        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(10);

        assertEquals(Integer.valueOf(10), bounds.getFirst());
        assertEquals(Integer.valueOf(15), bounds.getSecond());
    }

    @Test
    public void calculateScoreBounds_score99_returnsBounds95to100() {
        Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(99);

        assertEquals(Integer.valueOf(95), bounds.getFirst());
        assertEquals(Integer.valueOf(100), bounds.getSecond());
    }

    @Test
    public void calculateScoreBounds_lowerAlwaysLessThanUpper() {
        for (int i = 0; i <= 100; i++) {
            Pair<Integer, Integer> bounds = scoreService.calculateScoreBounds(i);
            assertTrue("Lower bound should be < upper bound for score " + i,
                    bounds.getFirst() < bounds.getSecond());
        }
    }

    // --- calculateScore tests ---

    @Test
    public void calculateScore_returnsZero() {
        Diamond diamond = new Diamond();
        Integer score = scoreService.calculateScore(diamond);

        assertEquals(Integer.valueOf(0), score);
    }
}
