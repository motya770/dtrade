package com.dtrade.controller;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/coin-payment/", method = RequestMethod.POST)
public class CoinPaymentsController {

    @RequestMapping(value = "/notify")
    public void notifyNew(@RequestBody String body, @RequestHeader HttpHeaders headers, HttpServletRequest httpServletRequest) {
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

        Map<String, String[]> map = httpServletRequest.getParameterMap();
        map.forEach((k, v)->{
            System.out.println("K:" + k + " V:" + v);
        });

        System.out.println("BODY " + body);
        headers.forEach((k, v)-> System.out.println("K:" + k + ", " + "V: " + v));

        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
        System.out.println("!!!!!!!!!!!!!! ");
    }
}
