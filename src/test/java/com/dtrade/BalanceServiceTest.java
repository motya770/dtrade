package com.dtrade;


import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
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

        List<Balance> balances = balanceService.getBalancesByAccount(account);
        Assert.assertTrue(balances.size()>0);

        balances.forEach(balance -> {
            Assert.assertTrue(balanceService.getBaseCurrencies().contains(balance.getCurrency()));
        });
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testUpdateBalance(){
        Account account = accountService.getStrictlyLoggedAccount();

        List<Balance> balances = balanceService.getBalancesByAccount(account);

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
    @Test
    public void testUpdateRoboBalances(){


        for (Diamond diamond : diamondService.getAllDiamonds()) {

            String roboMail = accountService.getRoboAccountMail(diamond, 0);
            Account roboAccount =  accountService.findByMail(roboMail);
            List<Currency> currencies = balanceService.getBaseCurrencies();


            List<Balance> balances = balanceService.getBalancesByAccount(roboAccount);

            balanceService.updateRoboBalances(currencies.get(currencies.indexOf(Currency.BTC)), roboAccount);

            Balance rereadBalance = balanceService.getBalance(Currency.BTC, roboAccount);

            //TODO think about this

        }
        
        //balanceService.updateRoboBalances();

    }

    /*
    Balance unfreezeAmount(Currency currency, Account account, BigDecimal amount);

    Balance freezeAmount(Currency currency, Account account, BigDecimal amount);

    void updateOpenSum(TradeOrder tradeOrder, Account account, BigDecimal sum, BigDecimal amount);

    Balance updateBalance(Currency currency, Account account, BigDecimal addedValue);

    Balance updateOpen(Currency currency, Account account, BigDecimal amount);

    Balance updateFrozenBalance(Currency currency, Account account, BigDecimal amount);

    Balance getBalance(Currency currency, Account account);

    BigDecimal getActualBalance(Currency currency, Account account);

    BigDecimal getOpenSum(Currency currency, Account account);

    BigDecimal getFrozen(Currency currency, Account account);*/
}



