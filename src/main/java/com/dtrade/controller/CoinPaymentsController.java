package com.dtrade.controller;
import com.dtrade.service.ICoinPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/notify")
    public void notifyNew(@RequestBody String body, @RequestParam Map<String,String> allRequestParams,
                          @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) {
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

        allRequestParams.forEach((k, v)->{
            System.out.println("K:" + k + " V:" + v);
        });

        String ipn_version = allRequestParams.get("ipn_version");
        String ipn_type = allRequestParams.get("ipn_type");
        String ipn_mode = allRequestParams.get("ipn_mode");
        String ipn_id =  allRequestParams.get("ipn_id");
        String merchant =  allRequestParams.get("merchant");
        String address = allRequestParams.get("address");
        String txn_id = allRequestParams.get("txn_id");
        String status = allRequestParams.get("status");
        String status_text = allRequestParams.get("status_text");
        String currency = allRequestParams.get("currency");
        String confirms =   allRequestParams.get("confirms");
        BigDecimal amount =  new BigDecimal(allRequestParams.get("amount"));

        System.out.println("BODY " + body);
        headers.forEach((k, v)-> System.out.println("K:" + k + ", " + "V: " + v));

        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");




    }
}
