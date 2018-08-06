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

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Transactional
public class CoinPaymentTests extends BaseTest{

    @Autowired
    private ICoinPaymentService coinPaymentService;

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    //@Test
    public void processDeposit(){
//Process
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
   // @Test
    public void createWithdrawTest(){

        //{"currencyCoin":"ETH","currencyFiat":"USD","address":"0x10D75F90b0F483942aDd5a947b71D8617BB012eD","amount":"2"}

        String currencyCoin ="ETH";
        //String currencyFiat = "USD";
        String address = "0x10D75F90b0F483942aDd5a947b71D8617BB012eD";
        String amount = "0.0001";

        coinPaymentService.sendWithdraw(
                InWithdrawRequest.initiliazeRequest(currencyCoin, address, amount)
        );

    }

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
   // @Test
    public void checkHmacTest(){

/*
        app_1         | you hmac: b1dfbc917ef67a19a25f35dfbc394a60722639044cd99cefd78e064029cfd10fb99fc18140b4a1f0634346a51568705b71034562a72be6475915655d49df6cb7
        app_1         | calculatedHmac: f6a314409c34af5fb5bb496f9fce0bbffb4bd1858cf755c9249cc8febbbb6c4449e31e005cdf56fdf0b19d0b590a69fb9c003083fa38b2621f5e10233cd4c9fd
        app_1         | 2018-06-02 18:25:03.596 ERROR 45651a3da6ee --- [io-8080-exec-18] c.d.s.i.TradeOrderService                : Type button is unknown
        app_1         | BODY: amount1=2&amount2=0.0034&currency1=USD&currency2=ETH&email=motya770%40gmail.com&fee=2.0E-5&first_name=m&ipn_id=e80e20869f5e458ef82db1f254026717&ipn_mode=hmac&ipn_type=button&ipn_version=1.0&item_amount=2&item_name=Diaminds+Deposit&last_name=k&merchant=1fb3cd572acffff43b1c0356d5429f1c&quantity=1&received_amount=0&received_confirms=0&shipping=0&status=0&status_text=Waiting+for+buyer+funds...&subtotal=2&tax=0&txn_id=CPCF15XF1DEECKLH6VLNH0ERKB
*/

        String body = "amount1=2&amount2=0.0034&currency1=USD&currency2=ETH&email=motya770%40gmail.com&fee=2.0E-5&first_name=m&ipn_id=e80e20869f5e458ef82db1f254026717&ipn_mode=hmac&ipn_type=button&ipn_version=1.0&item_amount=2&item_name=Diaminds+Deposit&last_name=k&merchant=1fb3cd572acffff43b1c0356d5429f1c&quantity=1&received_amount=0&received_confirms=0&shipping=0&status=0&status_text=Waiting+for+buyer+funds...&subtotal=2&tax=0&txn_id=CPCF15XF1DEECKLH6VLNH0ERKB";
             //  .replaceAll("\\+", "%20");
        String hmac = "b1dfbc917ef67a19a25f35dfbc394a60722639044cd99cefd78e064029cfd10fb99fc18140b4a1f0634346a51568705b71034562a72be6475915655d49df6cb7";
        coinPaymentService.checkHmac(hmac, body);

    }
}
