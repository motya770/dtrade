package com.dtrade;

import com.dtrade.controller.CoinPaymentsController;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.InWithdrawRequest;
import com.dtrade.service.ICoinPaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CoinPaymentTests extends BaseTest{

    @Autowired
    private ICoinPaymentService coinPaymentService;

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void processDeposit(){

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setEmail(F_DEFAULT_TEST_ACCOUNT);
        UUID uuid = UUID.randomUUID();
        depositRequest.setIpnId(uuid.toString());
        depositRequest.setIpn_version("1.0");
        depositRequest.setIpn_type("button");
        depositRequest.setIpn_mode("hmac");
        depositRequest.setMerchant("merchant");
        depositRequest.setAddress("test");
        depositRequest.setTransactionId("test1");
        depositRequest.setStatus(1);
        depositRequest.setStatus_text("test statussets");
        depositRequest.setConfirms("1");
        depositRequest.setCurrencyUsd("USD");
        depositRequest.setCurrencyCoin("ETHER");
        depositRequest.setAmountCoin(new BigDecimal("10"));
        depositRequest.setAmountUsd(new BigDecimal("100"));
        coinPaymentService.proceedDeposit(depositRequest);
    }

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void createWithdrawTest(){

        //{"currencyCoin":"ETH","currencyFiat":"USD","address":"0x10D75F90b0F483942aDd5a947b71D8617BB012eD","amount":"2"}

        String currencyCoin ="ETH";
        String currencyFiat = "USD";
        String address = "0x10D75F90b0F483942aDd5a947b71D8617BB012eD";
        String amount = "2";

        coinPaymentService.createWithdraw(
                InWithdrawRequest.initiliazeRequest(currencyCoin, currencyFiat, address, amount)
        );

    }
}
