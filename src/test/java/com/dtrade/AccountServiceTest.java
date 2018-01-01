package com.dtrade;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.service.IAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kudelin on 12/7/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest extends BaseTest{

    @Autowired
    private IAccountService accountService;

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    @Transactional
    public void testCheckCurrentAccount(){
      Account account = accountService.getCurrentAccount();
      accountService.checkCurrentAccount(account);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetCurrentAccount(){
        Account account = accountService.getCurrentAccount();
        Assert.assertTrue(account.getMail().equals(F_DEFAULT_TEST_ACCOUNT));
    }

    @Test
    public void testGetStrictlyLoggedAccount(){
        Account account = null;
        try {
            account = accountService.getStrictlyLoggedAccount();
        }catch (Exception e){
            Assert.assertTrue(e instanceof TradeException);
        }
        Assert.assertNull(account);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testEnableDisable(){
        Account account  = accountService.getCurrentAccount();
        accountService.disable(account.getId());

        Account rereadAccount = accountService.find(account.getId());
        Assert.assertTrue(!rereadAccount.isEnabled());

        accountService.enable(account.getId());
        rereadAccount = accountService.find(account.getId());

        Assert.assertTrue(rereadAccount.isEnabled());
    }

    @Test
    public void testFindAll(){
          accountService.findAll();

    }

    public void testFind(){

    }

    public void testCreate(){

    }

    public void testConfirmRegistration(){

    }

    public void testCancelRegistration(){

    }

    public void testCreateRealAccount(){

    }


   public  void testSave(){

   }

   public void testUpdateBalance(){

   }

    public void testFindByMail(){

   }

}
