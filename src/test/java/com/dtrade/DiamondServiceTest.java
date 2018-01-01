package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.service.impl.AccountService;
import com.dtrade.service.impl.DiamondService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by kudelin on 12/7/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DiamondServiceTest  extends BaseTest{

    @Autowired
    private DiamondService diamondService;


/*
    Diamond find(Long diamondId);

    List<Diamond> getAvailable();

    List<Diamond> getAllAvailable();

    Page<Diamond> getAllDiamonds();

    List<Diamond> getDiamondsByScoreBounds(int lowerBound, int upperBound);

    Diamond create(Diamond diamond);*/

    @Autowired
    private AccountService accountService;

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    @Transactional
    public void testUpdate(){

        Account currentAccount = accountService.getCurrentAccount();
        createDiamond();

        Diamond diamond = diamondService.getAvailable().stream().filter((d)-> d.getAccount().getId()
                .equals(currentAccount.getId())).findFirst().get();
        BigDecimal carats = new BigDecimal("10.0");
        diamond.setCarats(carats);
        diamond = diamondService.update(diamond);
        Assert.assertTrue(diamond.getCarats().equals(carats));

    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    @Transactional
    public void testCheckDiamondOwnship(){

        Account currentAccount = accountService.getCurrentAccount();
        createDiamond();
        Diamond diamond = diamondService.getAvailable().stream().filter((d)-> d.getAccount().getId()
                .equals(currentAccount.getId())).findFirst().get();
        Assert.assertNotNull(diamond);
        diamondService.checkDiamondOwnship(currentAccount, diamond);

    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    @Transactional
    public void testFind(){

        Diamond diamond = createDiamond();
        Diamond foundDiamond = diamondService.find(diamond.getId());

        Assert.assertTrue(diamond.getId().equals(foundDiamond.getId()));
        Assert.assertTrue(diamond.getCarats().equals(foundDiamond.getCarats()));
        Assert.assertTrue(Objects.equals(diamond, foundDiamond));

    }

    @Test
    @Transactional
    public void testGetAvialibale(){

        List<Diamond> diamonds = diamondService.getAvailable();
        diamonds.forEach((d)->{
            Assert.assertTrue(d.getDiamondStatus().equals(DiamondStatus.ENLISTED));
        });

    }

    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testAllDiamonds(){
        Page<Diamond> diamonds = diamondService.getAllDiamonds();
        long totalCount = diamonds.getTotalElements();
        createDiamond();
        long totalNewCount = diamondService.getAllDiamonds().getTotalElements();
        Assert.assertTrue(totalCount+1 == totalNewCount);

    }

    @Test
    public void testDiamondsByScoreBounds(){
        //TODO rewrite this test
        List<Diamond> diamonds = diamondService.getDiamondsByScoreBounds(0, 100);
        Assert.assertTrue(diamonds.size()>0);

    }

  //  @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testCreate(){
        Diamond diamond = createDiamond();
        Assert.assertNotNull(diamond);
        //TODO fix this bug
        Assert.assertTrue(diamond.getDiamondStatus().equals(DiamondStatus.CREATED));
    }

}
