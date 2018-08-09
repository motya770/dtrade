package com.dtrade;

import com.dtrade.controller.CoinPaymentsController;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.InWithdrawRequest;
import com.dtrade.model.currency.Currency;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceService;
import com.dtrade.service.ICoinPaymentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceService balanceService;

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testProcessDeposit(){
        DepositRequest depositRequest = createDepositRequest();

        BigDecimal amount  = depositRequest.getAmountCoin();
        Currency currency = Currency.valueOf(depositRequest.getCurrencyCoin());
        Account account = accountService.getStrictlyLoggedAccount();

        BigDecimal saved = balanceService.getBalance(currency, account).getAmount();
        coinPaymentService.proceedDeposit(depositRequest);
        Balance reread = balanceService.getBalance(currency, account);

        Assert.assertTrue(saved.add(amount).compareTo(reread.getAmount())==0);
    }

    //real test of coin payments
    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCreateWithdraw(){
        String currencyCoin ="ETH";
        String address = "0x10D75F90b0F483942aDd5a947b71D8617BB012eD";
        String amount = "0.00240000";

        CoinPayment coinPayment =  coinPaymentService.sendWithdraw(
                InWithdrawRequest.initiliazeRequest(currencyCoin, address, amount)
        );

        Page<CoinPayment> payments =  coinPaymentService.findAll(0);

        long count = payments.getContent().stream().filter(p -> p.getId().equals(coinPayment)).count();

        Assert.assertEquals(1L, count);
    }


    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testProceedWithdraw(){
        String currencyCoin ="ETH";
        String address = "0x10D75F90b0F483942aDd5a947b71D8617BB012eD";
        String amount = "0.00240000";

        CoinPayment coinPayment =  coinPaymentService.sendWithdraw(
                InWithdrawRequest.initiliazeRequest(currencyCoin, address, amount)
        );

        coinPayment.getInWithdrawRequest().setStatus(2);

        Account account = accountService.getStrictlyLoggedAccount();
        BigDecimal savedBalance = balanceService.getBalance(Currency.valueOf(currencyCoin), account).getAmount();

        coinPaymentService.proceedWithdraw(coinPayment.getInWithdrawRequest());

        Balance balance = balanceService.getBalance(Currency.valueOf(currencyCoin), account);

        Assert.assertTrue(savedBalance.subtract(new BigDecimal(amount)).compareTo(balance.getAmount())==0);

    }

    //CoinPayment sendWithdraw(InWithdrawRequest outWithdrawRequest);

    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
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




/*
    CoinPayment createWithdraw(InWithdrawRequest outWithdrawRequest);

    CoinPayment createDeposit(DepositRequest depositRequest);

    Page<CoinPayment> getAllByAccount(Account account);

    CoinPayment confirmPayment(CoinPayment coinPayment);

    CoinPayment confirmWithdraw(CoinPayment coinPayment);

    void checkHmac(String hmac, String body);*/
}
