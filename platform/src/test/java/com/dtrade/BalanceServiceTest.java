package com.dtrade;


import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.balance.BalancePos;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.balance.BalanceRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceService;
import com.dtrade.service.IDiamondService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BalanceServiceTest extends BaseTest {

    @Autowired
    private IBalanceService balanceService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IDiamondService diamondService;

    @Test
    public void testGetBaseCurrencies(){
        List<Currency> currencies = balanceService.getBaseCurrencies();
        Assert.assertTrue(currencies.size() > 0);

        Assert.assertTrue(currencies.contains(Currency.BTC));
        Assert.assertTrue(currencies.contains(Currency.USD));
        Assert.assertTrue(currencies.contains(Currency.ETH));
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetBalancesByAccount(){
        Account account = accountService.getStrictlyLoggedAccount();

        List<BalancePos> balances = balanceService.getBalancesByAccount(account);
        Assert.assertTrue(balances.size()>0);

        balances.forEach(balance -> {
            Assert.assertTrue(balance.getAmount().compareTo(BigDecimal.ZERO)>0);
        });
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testUpdateBalance1(){
        Account account = accountService.getStrictlyLoggedAccount();

        List<BalancePos> balances = balanceService.getBalancesByAccount(account);

        List<Balance> saved = new ArrayList<>();

        BigDecimal incValue = new BigDecimal("10000");

        List<Balance> updated =new ArrayList<>();

        for(Balance balance: balances){

            Balance s = new Balance();
            s.setId(balance.getId());
            s.setAmount(balance.getAmount());
            saved.add(s);

            balance.setAmount(balance.getAmount().add(incValue));
            balance = balanceService.updateBalance(balance);
            updated.add(balance);
        }

        updated.forEach(balance -> {
            for(Balance b: saved){
                if(balance.getId().equals(b.getId())){
                    Assert.assertTrue(b.getAmount().add(incValue).compareTo(balance.getAmount())==0);
                }
            }
        });
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    //@Test
    public void testUpdateRoboBalances(){
        for (Diamond diamond : diamondService.getAllDiamonds()) {

            String roboMail = accountService.getRoboAccountMail(diamond, 0);
            Account roboAccount =  accountService.findByMail(roboMail);
            List<Currency> currencies = balanceService.getBaseCurrencies();

            List<BalancePos> balances = balanceService.getBalancesByAccount(roboAccount);
            Balance btcBalnce = balances.stream().filter(b->b.getCurrency().equals(Currency.BTC)).findFirst().get();
            BigDecimal amount = btcBalnce.getAmount();
            Assert.assertTrue(amount.compareTo(BigDecimal.ZERO)==0);

            balanceService.updateRoboBalances(currencies.get(currencies.indexOf(Currency.BTC)), roboAccount);
            Balance rereadBalance = balanceService.getBalance(Currency.BTC, roboAccount);
            Assert.assertTrue(amount.add(new BigDecimal("100000")).compareTo(rereadBalance.getAmount())==0);
        }
    }


    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void freezeAndUnfreeze(){

        //freeze
        Account account = accountService.getStrictlyLoggedAccount();
        Currency currency = Currency.BTC;
        BigDecimal amount = new BigDecimal("1234");

        BigDecimal initialFrozen = balanceService.getFrozen(currency, account);

        balanceService.freezeAmount(currency, account, amount);

        BigDecimal updatedFrozen = balanceService.getFrozen(currency, account);
        Assert.assertTrue(initialFrozen.add(amount).compareTo(updatedFrozen)==0);

        //unfreeze
        Balance balance = balanceService.unfreezeAmount(currency, account, amount);
        Assert.assertTrue(balance.getFrozen().compareTo(initialFrozen)==0);
    }


    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testUpdateOpenSum(){

        TradeOrder order = createTestTradeOrder(TradeOrderDirection.SELL);

        BigDecimal amount = order.getPrice().multiply(order.getAmount());
        Account account = accountService.getStrictlyLoggedAccount();

        Balance balance = balanceService.getBalance(order.getDiamond().getBaseCurrency(), account);
        BigDecimal sum = order.getPrice().multiply(amount);

        BigDecimal open = balance.getOpen();
        balanceService.updateOpenSum(order, account, amount, sum);

        Balance rereadBalance = balanceService.getBalance(order.getDiamond().getBaseCurrency(), account);
        //TODO fix open test
        // Assert.assertTrue(rereadBalance.getOpen().compareTo(open.add(amount))==0);
    }

    @Autowired
    private BalanceRepository balanceRepository;

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testUpdateBalance(){
        Account account = accountService.getStrictlyLoggedAccount();

        Balance balance = balanceService.getBalance(Currency.BTC, account);
        BigDecimal saved = balance.getAmount();
        BigDecimal amount = new BigDecimal("0.2342342");
        balanceService.updateBalance(Currency.BTC, account, amount);
        /*
        Account account1 = accountService.findByMail(F_DEFAULT_TEST_ACCOUNT);
        account1.setPhone("05289163872");
        accountService.save(account1);*/

        balance.setAmount(balance.getAmount().add(amount));
        balanceService.updateBalance(balance);

        long start = System.currentTimeMillis();
        //Balance rereadBalance = account.getBalances().stream().filter(balance2 -> balance2.getCurrency()==Currency.BTC).findFirst().get();
        Balance rereadBalance = balanceRepository.findById(40398L).get(); // balanceService.getBalance(Currency.BTC, account);
        //Assert.assertTrue(rereadBalance.getAmount().compareTo(saved.add(amount).add(amount))==0);
        System.out.println(System.currentTimeMillis() - start);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetBalance(){
        Account account = accountService.getStrictlyLoggedAccount();
        Balance balance = balanceService.getBalance(Currency.BTC, account);
        Assert.assertNotNull(balance);
        Assert.assertTrue(balance.getAmount().compareTo(BigDecimal.ZERO)>0);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetActualBalance(){
        Account account = accountService.getStrictlyLoggedAccount();
        Balance balance = balanceService.getBalance(Currency.BTC, account);
        BigDecimal actualBalance = balanceService.getActualBalance(Currency.BTC, account);
        BigDecimal computed = balance.getAmount().subtract(balance.getOpen()).subtract(balance.getFrozen());
        Assert.assertTrue(actualBalance.compareTo(computed)==0);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testUpdateOpen(){
        Account account = accountService.getStrictlyLoggedAccount();
        BigDecimal amount = new BigDecimal("0.12323333");
        Currency currency = Currency.ETH;

        BigDecimal savedOpen = balanceService.getOpenSum(currency, account);
        Balance balance = balanceService.updateOpen(currency, account, amount);
        Assert.assertTrue(balance.getOpen().compareTo(savedOpen.add(amount))==0);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testUpdateFrozenBalance() {
        Account account = accountService.getStrictlyLoggedAccount();
        BigDecimal amount = new BigDecimal("0.12323333");
        Currency currency = Currency.ETH;
        BigDecimal savedFrozen = balanceService.getFrozen(currency, account);
        Balance balance = balanceService.updateFrozenBalance(currency, account, amount);
        Assert.assertTrue(balance.getFrozen().compareTo(savedFrozen.add(amount))==0);
    }
}



