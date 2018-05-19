package com.dtrade.controller;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.CoinPaymentRequest;
import com.dtrade.repository.coinpayment.CoinPaymentRepository;
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

        params.forEach((k, v)->{
            System.out.println("K:" + k + " V:" + v);
        });

        CoinPaymentRequest coinPaymentRequest = new CoinPaymentRequest();
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

        coinPaymentRequest.setIpn_version(ipn_version);
        coinPaymentRequest.setIpn_type(ipn_type);
        coinPaymentRequest.setIpn_mode(ipn_mode);
        coinPaymentRequest.setIpnId(ipn_id);
        coinPaymentRequest.setMerchant(merchant);
        coinPaymentRequest.setAddress(address);
        coinPaymentRequest.setTransactionId(txn_id);
        coinPaymentRequest.setStatus(status);
        coinPaymentRequest.setCurrencyUsd(currencyUsd);
        coinPaymentRequest.setCurrencyCoin(currencyCoin);
        coinPaymentRequest.setStatus_text(status_text);
        coinPaymentRequest.setConfirms(confirms);

        coinPaymentRequest.setAmountUsd(amountUsd);
        coinPaymentRequest.setAmountCoin(amountCoin);
        coinPaymentRequest.setEmail(email);

        System.out.println("BODY " + body);
        headers.forEach((k, v)-> System.out.println("K:" + k + ", " + "V: " + v));

        coinPaymentService.proceed(coinPaymentRequest);

        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");




    }
}
