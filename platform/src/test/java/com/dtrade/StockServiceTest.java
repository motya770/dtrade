package com.dtrade;


import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.stock.StockDTO;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IStockService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StockServiceTest extends BaseTest {

    @Autowired
    private IStockService stockService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IDiamondService diamondService;

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetStocksByAccount(){
       List<StockDTO> stockDTOS = stockService.getStocksByAccount();
       Assert.assertNotNull(stockDTOS);
       Assert.assertTrue(stockDTOS.size()>0);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    //@Test
    public void testFindAllAndGetSpesicicStock(){


        BigDecimal amount =new BigDecimal("1233");
        BigDecimal stockInTrade =new BigDecimal("100000");

        Account account = accountService.getStrictlyLoggedAccount();
        Stock stock = new Stock();
        stock.setAmount(amount);
        stock.setStockInTrade(stockInTrade);
        stock.setAccount(account);
        Diamond diamond = diamondService.getAllAvailable("").stream().findFirst().get();
        stock.setDiamond(diamond);

        final Stock saved = stockService.save(stock);

        Assert.assertTrue(stockService.fieldsNotEmpty(saved));

        List<Stock> stocks = stockService.findAll();
        long counter =  stocks.stream().filter(s -> s.getId().equals(saved.getId())).count();

        Assert.assertEquals(1, counter);

        Stock reread = stockService.getSpecificStock(account, stock.getDiamond());

        Assert.assertNotNull(reread);
        Assert.assertTrue(reread.getAmount().compareTo(amount)==0);
        Assert.assertTrue(reread.getStockInTrade().compareTo(stockInTrade)==0);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testMakeIPO() {

        Diamond diamond = diamondService.getAllAvailable("").stream().findFirst().get();
        Assert.assertNotNull(diamond);
        //TODO rewrite this test (maybe)
        try {
            stockService.makeIPO(diamond.getId());
        }catch (TradeException e) {

        }
        Assert.assertTrue(diamond.getTotalStockAmount().compareTo(BigDecimal.ZERO)>0);
    }


    /*
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetSpecificStock() {
        Diamond diamond = diamondService.getAllAvailable("").stream().findFirst().get();
        Account account = accountService.getStrictlyLoggedAccount();

        Assert.assertNotNull(stock);
    }*/

/*
    Stock updateStockInTrade(TradeOrder tradeOrder, Account account, Diamond diamond, BigDecimal stockAmount);

    Stock updateRoboStockAmount(Diamond diamond, Account account);

    void save(Stock stock);
    */
}
