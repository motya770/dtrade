package com.dtrade.controller;

import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.WithdrawRequest;
import com.dtrade.service.IAccountService;
import com.dtrade.service.ICoinPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(value = "/coin-payment/", method = RequestMethod.POST)
public class CoinPaymentsController {

    @Autowired
    private ICoinPaymentService coinPaymentService;

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/get-by-account")
    public Page<CoinPayment> getByAccount(){
        return coinPaymentService.getAllByAccount(accountService.getStrictlyLoggedAccount());
    }

    @RequestMapping(value = "/create-withdraw")
    public CoinPayment createWithdraw(@RequestParam String currencyCoin, @RequestParam String currencyFiat,
                                      @RequestParam String address,
                                      @RequestParam String amount){
       return coinPaymentService.createWithdraw(
               WithdrawRequest.build(currencyCoin, currencyFiat, address, amount)
       );
    }

    /*
    cmd	create_withdrawal	Yes
    amount	The amount of the withdrawal in the currency below.	Yes
    add_tx_fee	If set to 1, add the coin TX fee to the withdrawal amount so the sender pays the TX fee instead of the receiver.	No
    currency	The cryptocurrency to withdraw. (BTC, LTC, etc.)	Yes
    currency2	Optional currency to use to to withdraw 'amount' worth of 'currency2' in 'currency' coin. This is for exchange rate calculation only and will not convert coins or change which currency is withdrawn.
    For example, to withdraw 1.00 USD worth of BTC you would specify 'currency'='BTC', 'currency2'='USD', and 'amount'='1.00'	No
    address	The address to send the funds to, either this OR pbntag must be specified.
            Remember: this must be an address in currency's network.	See Desc
    pbntag	The $PayByName tag to send the withdrawal to, either this OR address must be specified. This will also override any destination tag specified.	See Desc
    dest_tag	The extra tag to use for the withdrawal for coins that need it (Destination Tag for Ripple, Payment ID for Monero, Message for XEM, etc.)	No
    ipn_url	URL for your IPN callbacks. If not set it will use the IPN URL in your Edit Settings page if you have one set.	No
    auto_confirm	If set to 1, withdrawal will complete without email confirmation.	No
    note	This lets you set the note for the withdrawal.
   */

    @RequestMapping(value = "/notify")
    public void notifyNew(@RequestBody String body, @RequestParam Map<String,String> params,
                          @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) {

        //add HMAC security
        /*
        ipn_version	1.0	Yes
        ipn_type	Currently: 'simple, 'button', 'cart', 'donation', 'deposit', or 'api'	Yes
        ipn_mode	Currently: 'hmac'	Yes
        ipn_id	The unique identifier of this IPN	Yes
        merchant	Your merchant ID (you can find this on the My Account page).*/

        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("NOTIFY: " + httpServletRequest.toString());

        String hmac = headers.getFirst("hmac");

        coinPaymentService.checkHmac(hmac, body);

        params.forEach((k, v)->{
            System.out.println("K:" + k + " V:" + v);
        });

        DepositRequest depositRequest = new DepositRequest();
        String ipn_version = params.get("ipn_version");
        String ipn_type = params.get("ipn_type");
        String ipn_mode = params.get("ipn_mode");
        String ipn_id =  params.get("ipn_id");
        String merchant =  params.get("merchant");
        String address = params.get("address");
        String txn_id = params.get("txn_id");
        Integer status =  Integer.parseInt(params.get("status"));
        String status_text = params.get("status_text");
        String currencyUsd = params.get("currency1");
        String currencyCoin = params.get("currency2");

        String confirms =   params.get("confirms");
        BigDecimal amountUsd =  new BigDecimal(params.get("amount1"));
        BigDecimal amountCoin =  new BigDecimal(params.get("amount2"));
        String email =   params.get("email");

        depositRequest.setIpn_version(ipn_version);
        depositRequest.setIpn_type(ipn_type);
        depositRequest.setIpn_mode(ipn_mode);
        depositRequest.setIpnId(ipn_id);
        depositRequest.setMerchant(merchant);
        depositRequest.setAddress(address);
        depositRequest.setTransactionId(txn_id);
        depositRequest.setStatus(status);
        depositRequest.setCurrencyUsd(currencyUsd);
        depositRequest.setCurrencyCoin(currencyCoin);
        depositRequest.setStatus_text(status_text);
        depositRequest.setConfirms(confirms);

        depositRequest.setAmountUsd(amountUsd);
        depositRequest.setAmountCoin(amountCoin);
        depositRequest.setEmail(email);

        System.out.println("BODY " + body);
        headers.forEach((k, v)-> System.out.println("K:" + k + ", " + "V: " + v));

        coinPaymentService.proceedDeposit(depositRequest);

        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
    }
}
