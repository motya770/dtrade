package com.dtrade;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.service.IAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kudelin on 12/7/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
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
        Page<Account> accounts = accountService.findAll(0);
        Assert.assertNotNull(accounts);
        Assert.assertTrue(accounts.getContent().size() > 0);

        long currentSize = accounts.getTotalElements();

        createAccount();

        Assert.assertTrue(accountService.findAll(0).getTotalElements() == currentSize + 1);
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testFind(){
        Account currentAccount = accountService.getCurrentAccount();
        Account foundAccount = accountService.find(currentAccount.getId());
        Assert.assertTrue(foundAccount.getId().equals(foundAccount.getId()));

    }

    @Test
    public void testCreate(){
        Account account = createAccount();
        Account foundAccount = accountService.find(account.getId());
        Assert.assertTrue(account.getId().equals(foundAccount.getId()));
    }

    //TODO write later
    public void testConfirmRegistration(){

    }

    public void testCancelRegistration(){

    }

    public void testCreateRealAccount(){

    }

    /*
   @Test
   public  void testSave(){

        Account account = createAccount();

        BigDecimal balance = new BigDecimal("10");
        account.setBalance(balance);
        account = accountService.save(account);
        Assert.assertTrue(account.getBalance().equals(balance));
   }
*/
   /*
   @Test
   public void testUpdateBalance(){
        Account account = createAccount();
        BigDecimal oldBalance = account.getBalance();
        BigDecimal addedValue =  new BigDecimal("59");
        account = accountService.updateBalance(account, addedValue);

        Assert.assertTrue(account.getBalance().compareTo(oldBalance.add(addedValue)) == 0);
   }*/

   @Test
   public void testFindByMail(){
       Account account =  accountService.findByMail(F_DEFAULT_TEST_ACCOUNT);
       Assert.assertNotNull(account);
       Assert.assertNotNull(F_DEFAULT_TEST_ACCOUNT.equals(account.getMail()));
   }

}
