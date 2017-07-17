package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.service.IAccountService;
import com.dtrade.service.ITradeOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by kudelin on 7/15/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeOrderServiceTest {

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private IAccountService accountService;

    private SecurityContext setAccount(Account account){
        UserDetails principal = account;
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    @Test
    public void test(){

        Account account = accountService.createRealAccount("motya7702@gmail.com", "12345", "+79210827487", "USD");


        accountService.confirmRegistration(account.getGuid());

        SecurityContextHolder.setContext(setAccount(account));

        System.out.println("1233 234 " + tradeOrderService);

    }
}



