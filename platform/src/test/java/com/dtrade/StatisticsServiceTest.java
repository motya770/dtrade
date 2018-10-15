package com.dtrade;


import com.dtrade.service.IStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StatisticsServiceTest {

    @Autowired
    private IStatisticsService statisticsService;

    @Test
    public void testSellSumForMonthForAccount(){
        statisticsService.sellSumForMonthForAccount();
    }

    @Test
    public void testBuySumForMonthForAccount(){
        statisticsService.buySumForMonthForAccount();
    }
}
