package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.balanceactivity.BalanceActivityType;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.InWithdrawRequest;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.balance.BalanceRepository;
import com.dtrade.repository.balanceactivity.BalanceActivityRepository;
import com.dtrade.service.IBalanceService;
import com.dtrade.service.ICoinPaymentService;
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

    @Autowired
    private ICoinPaymentService coinPaymentService;

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



//        if(coinPayment.getAccount()==null){
//            throw new TradeException("Account is null");
//        }
//
//        Account account = accountService.find(coinPayment.getAccount().getId());
//        Currency currency = Currency.valueOf(coinPayment.getDepositRequest().getCurrencyCoin());
//        balanceService.updateBalance(currency, account,  coinPayment.getDepositRequest().getAmountCoin());
//
//        BalanceActivity ba = new BalanceActivity();
//        ba.setAccount(account);
//        ba.setBalanceActivityType(BalanceActivityType.DEPOSIT);
//        ba.setAmount(coinPayment.getDepositRequest().getAmountUsd());
//        ba.setCreateDate(System.currentTimeMillis());
//        ba.setCurrency(currency);
//        ba.setBalanceSnapshot(balanceService.getBalance(currency, account).getAmount());
//
//        return balanceActivityRepository.save(ba);

        Account account = accountService.getStrictlyLoggedAccount();

        Currency currency = Currency.ETH;
        BigDecimal amount = new BigDecimal("100000");

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setCurrencyCoin(currency.toString());
        depositRequest.setAmountCoin(amount);
        depositRequest.setIpnId("test");

        CoinPayment coinPayment = new CoinPayment();
        coinPayment.setDepositRequest(depositRequest);
        coinPayment.setAccount(account);

        Balance balance = balanceService.getBalance(currency, account);
        BigDecimal saved = balance.getAmount();

        balanceActivityService.createDepositBalanceActivity(coinPayment);

        balance = balanceService.getBalance(currency, account);

        Assert.assertTrue(balance.getAmount().compareTo(saved.add(amount))==0);
    }

    @Autowired
    private IBalanceService balanceService;

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCreateWithdrawBalanceActivity(){

        Account account = accountService.getStrictlyLoggedAccount();

        BigDecimal amount =  new BigDecimal("10000");

        CoinPayment coinPayment = new CoinPayment();
        coinPayment.setAccount(account);
        InWithdrawRequest withdrawRequest = new InWithdrawRequest();
        coinPayment.setInWithdrawRequest(withdrawRequest);
        coinPayment.getInWithdrawRequest().setAmountCoin(amount);
        coinPayment.getInWithdrawRequest().setCurrencyCoin(Currency.BTC.toString());

        coinPayment.setInWithdrawRequest(withdrawRequest);

        Currency currency = Currency.BTC;

        Balance balance = balanceService.getBalance(currency, account);
        BigDecimal saved = balance.getAmount();

        balanceActivityService.createWithdrawBalanceActivity(coinPayment);

        balance = balanceService.getBalance(currency, account);

        Assert.assertTrue(saved.subtract(amount).compareTo(balance.getAmount())==0);

//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=currency, rootBeanClass=class com.dtrade.model.balanceactivity.BalanceActivity, messageTemplate='{javax.validation.constraints.NotNull.message}'}
//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=sum, rootBeanClass=class com.dtrade.model.balanceactivity.BalanceActivity, messageTemplate='{javax.validation.constraints.NotNull.message}'}
//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=price, rootBeanClass=class com.dtrade.model.balanceactivity.BalanceActivity, messageTemplate='{javax.validation.constraints.NotNull.message}'}
//        ConstraintViolationImpl{interpolatedMessage='must not be null', propertyPath=balanceSnapshot,

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
