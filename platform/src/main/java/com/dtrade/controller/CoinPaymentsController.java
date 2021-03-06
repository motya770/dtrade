package com.dtrade.controller;

import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.InWithdrawRequest;
import com.dtrade.service.IAccountService;
import com.dtrade.service.ICoinPaymentService;
import com.dtrade.service.impl.TradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/coin-payment/", method = RequestMethod.POST)
public class CoinPaymentsController {

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    @Autowired
    private ICoinPaymentService coinPaymentService;

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/get-by-account")
    public Page<CoinPayment> getByAccount(){
        return coinPaymentService.getAllByAccount(accountService.getStrictlyLoggedAccount());
    }

    @RequestMapping(value = "/create-withdraw")
    public CoinPayment createWithdraw(@RequestParam String currencyCoin,
                                      @RequestParam String address,
                                      @RequestParam String amountCoin){

       return coinPaymentService.sendWithdraw(
               InWithdrawRequest.initiliazeRequest(currencyCoin, address, amountCoin)
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

    //add HMAC security
        /*
        ipn_version	1.0	Yes
        ipn_type	Currently: 'simple, 'button', 'cart', 'donation', 'deposit', or 'api'	Yes
        ipn_mode	Currently: 'hmac'	Yes
        ipn_id	The unique identifier of this IPN	Yes
        merchant	Your merchant ID (you can find this on the My Account page).*/

    @RequestMapping(value = "/notify")
    public void notifyNew(@RequestParam Map<String,String> params,
                          @RequestHeader HttpHeaders headers, @RequestBody String body) {

        //WE can't trust this request because i failed to decipher HMAC - so making new request to the server
        logger.info("!!!!!!!!!!!!!! ");
        logger.info("!!!!!!!!!!!!!! ");
        logger.info("!!!!!!!!!!!!!! ");
        logger.info("!!!!!!!!!!!!!! ");
        logger.info("NOTIFY! ");

        params.forEach((k, v)->{
            logger.info("K:" + k + " V:" + v);
        });

        headers.keySet().forEach(k->{
            logger.info("First header " +  headers.getFirst(k));
        });

        String hmac = headers.getFirst("hmac");
        coinPaymentService.checkHmac(hmac, body);

        String ipn_type = params.get("ipn_type");
        if(ipn_type.equals("withdrawal")){
            coinPaymentService.proceedWithdraw(InWithdrawRequest.build(params)); //.requestWithdraw(params.get("id"));
        }else if(ipn_type.equals("button")){
            coinPaymentService.proceedDeposit(DepositRequest.build(params)); //.requestDeposit(params.get("txn_id"));
        }else{
            logger.error("Type " + ipn_type + " is unknown");
        }

        logger.info("BODY: " + body);
        headers.forEach((k, v)-> logger.info("K:" + k + ", " + "V: " + v));

        logger.info("!!!!!!!!!!!!!! ");
        logger.info("!!!!!!!!!!!!!! ");
        logger.info("!!!!!!!!!!!!!! ");
        logger.info("!!!!!!!!!!!!!! ");
        logger.info("!!!!!!!!!!!!!! ");
    }
}
