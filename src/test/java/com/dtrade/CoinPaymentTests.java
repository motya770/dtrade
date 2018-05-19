package com.dtrade;

import com.dtrade.model.coinpayment.CoinPaymentRequest;
import com.dtrade.service.ICoinPaymentService;
import org.hibernate.id.GUIDGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Random;
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
    public void processTest(){

        CoinPaymentRequest coinPaymentRequest = new CoinPaymentRequest();
        coinPaymentRequest.setEmail(F_DEFAULT_TEST_ACCOUNT);
        UUID uuid = UUID.randomUUID();
        coinPaymentRequest.setIpnId(uuid.toString());
        coinPaymentRequest.setIpn_version("1.0");
        coinPaymentRequest.setIpn_type("button");
        coinPaymentRequest.setIpn_mode("hmac");
        coinPaymentRequest.setMerchant("merchant");
        coinPaymentRequest.setAddress("test");
        coinPaymentRequest.setTxn_id("test");
        coinPaymentRequest.setStatus(1);
        coinPaymentRequest.setStatus_text("test statussets");
        coinPaymentRequest.setConfirms("1");
        coinPaymentRequest.setCurrencyUsd("USD");
        coinPaymentRequest.setCurrencyCoin("ETHER");
        coinPaymentRequest.setAmountCoin(new BigDecimal("10"));
        coinPaymentRequest.setAmountUsd(new BigDecimal("100"));

        coinPaymentService.proceed(coinPaymentRequest);
    }

}
