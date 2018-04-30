package com.dtrade.service.impl;

import com.dtrade.service.ICoinPaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoinPaymentService implements ICoinPaymentService {


    private RestTemplate restTemplate = new RestTemplate();


    //template
    /*
     "curl --request POST \
             --url https://www.coinpayments.net/api.php \
            --header 'Content-Type: application/x-www-form-urlencoded' \
            --header 'HMAC: your_generated_hmac' \
            --data 'version=1&cmd=rates&key=your_api_public_key&format=json'"
    */

    @Override
    public void login() {

    }

    @Override
    public void deposit() {

    }

    @Override
    public void withdraw() {

    }
}
