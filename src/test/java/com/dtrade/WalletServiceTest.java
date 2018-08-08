package com.dtrade;


import com.dtrade.service.IWalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class WalletServiceTest {

    @Autowired
    private IWalletService walletService;

    @Test
    public void depositTokens() {
        walletService.depositTokens("test", new BigDecimal("0.12335"));
    }

    @Test
    public void withdrawTokens() {
        walletService.withdrawTokens("test", new BigDecimal("0.0234324"));

    }
}
