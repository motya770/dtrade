package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.service.impl.AccountService;
import com.dtrade.service.impl.BalanceActivityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kudelin on 12/7/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceActivityTest extends BaseTest {

    @Autowired
    private BalanceActivityService balanceActivityService;

    @Autowired
    private AccountService accountService;

    //TODO make more complicated checks
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCreateBalanceActivities(){
       Pair<BalanceActivity, BalanceActivity> pair =createTestBalanceActivities();
       Assert.assertNotNull(pair);
    }

    private Pair<BalanceActivity, BalanceActivity> createTestBalanceActivities(){
        Account buyer = accountService.getCurrentAccount();
        Account seller = accountService.findByMail("test@test.com");

        TradeOrder buyOrder = createTestBuyTradeOrder();
        TradeOrder sellOrder = createTestSellTradeOrder();

        Pair<BalanceActivity, BalanceActivity> pair = balanceActivityService.createBalanceActivities(buyer, seller, new BigDecimal("100"), buyOrder, sellOrder);
        return pair;
    }

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetAccountBalanceActivities(){
        Pair<BalanceActivity, BalanceActivity> pair = createTestBalanceActivities();

        List<BalanceActivity> balanceActivities = balanceActivityService.getAccountBalanceActivities();
        List<BalanceActivity> filtered = balanceActivities.stream().
                filter((balanceActivity)->
                    balanceActivity.getId().equals(pair.getFirst().getId()) || balanceActivity.getId().equals(pair.getSecond().getId()))
                .collect(Collectors.toList());

        Assert.assertTrue(filtered.size() == 1);
        BalanceActivity buyActivity = pair.getFirst();
        Assert.assertTrue( buyActivity.equals(filtered.get(0)));
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testFindAll(){
        List<BalanceActivity> balanceActivities = balanceActivityService.findAll();
        createTestBalanceActivities();
        List<BalanceActivity> rereadBalanceActivities = balanceActivityService.findAll();
        Assert.assertTrue((balanceActivities.size() + 2 == rereadBalanceActivities.size()));

    }
}
