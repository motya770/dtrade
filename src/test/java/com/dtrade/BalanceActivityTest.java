package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.balance.BalanceRepository;
import com.dtrade.repository.balanceactivity.BalanceActivityRepository;
import com.dtrade.service.impl.AccountService;
import com.dtrade.service.impl.BalanceActivityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
@Transactional
public class BalanceActivityTest extends BaseTest {

    @Autowired
    private BalanceActivityService balanceActivityService;

    @Autowired
    private AccountService accountService;


    @Autowired
    private BalanceActivityRepository balanceActivityRepository;

    //TODO make more complicated checks
    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCreateBalanceActivities(){
        Pair<TradeOrder, TradeOrder> pair =createTestBalanceActivities();
        Assert.assertNotNull(pair);
        Assert.assertNotNull(pair.getFirst());
        Assert.assertNotNull(pair.getSecond());
    }

    @Transactional
    public Pair<TradeOrder, TradeOrder> createTestBalanceActivities(){
        Account buyer = accountService.getCurrentAccount();
        Account seller = accountService.findByMail("test@test.com");

        TradeOrder buyOrder = createTestBuyTradeOrder();
        TradeOrder sellOrder = createTestSellTradeOrder();

        balanceActivityService.createBalanceActivities(buyer, seller, buyOrder,
                sellOrder,  sellOrder.getAmount(), sellOrder.getPrice());

        return Pair.of(buyOrder, sellOrder);
    }

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetAccountBalanceActivities(){

        Pair<TradeOrder, TradeOrder> pair = createTestBalanceActivities();

        List<BalanceActivity> buyActivities = balanceActivityRepository.findAllByBuyOrder(pair.getFirst());
        List<BalanceActivity> sellActivities = balanceActivityRepository.findAllByBuyOrder(pair.getSecond());

        Page<BalanceActivity> balanceActivities = balanceActivityService.getAccountBalanceActivities(0);

        List<BalanceActivity> filtered = balanceActivities.getContent().stream().
                filter(ac->
                        {
                            long counter = balanceActivities.stream()
                                   .filter(ba -> ba.getId().equals(ac.getId())).count();
                            counter += balanceActivities.stream()
                                   .filter(ba -> ba.getId().equals(ac.getId())).count();
                            return counter > 0;
                        })
                .collect(Collectors.toList());

        Assert.assertTrue(filtered.size() > 0);
        Assert.assertTrue( buyActivities.contains(filtered.get(0)));

        Page<BalanceActivity> activities = balanceActivityService.findAll(0);
        Assert.assertTrue( buyActivities.contains(activities.getContent().get(0)));
    }


    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCreateDepositBalanceActivity(){
        CoinPayment coinPayment = new CoinPayment();
        balanceActivityService.createDepositBalanceActivity(coinPayment);

    }

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCreateWithdrawBalanceActivity(){

        CoinPayment coinPayment = new CoinPayment();
        balanceActivityService.createWithdrawBalanceActivity(coinPayment);

    }


    /*
    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testBalanceAcitivtyCreated(){
        Pair<BalanceActivity, BalanceActivity> pair = createTestBalanceActivities();
        Page<BalanceActivity> rereadBalanceActivities = balanceActivityService.getAccountBalanceActivities(0);
        long count =
        rereadBalanceActivities.getContent().stream().filter(balanceActivity ->
             (balanceActivity.equals(pair.getFirst()) || balanceActivity.equals(pair.getSecond()))
        ).count();

        Assert.assertTrue(count == 2);
    }
    */
}
