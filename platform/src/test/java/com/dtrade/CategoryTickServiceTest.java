package com.dtrade;

import com.dtrade.service.ICategoryTickService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryTickServiceTest {

    @Autowired
    private ICategoryTickService categoryTickService;

    @Test
    public void testCreateCategoryQuote(){
        categoryTickService.createCategoryQuote(100, new BigDecimal("7500.232"));
    }

    @Test
    public void testGetByScore(){
        categoryTickService.getByScore(10);
    }
}
