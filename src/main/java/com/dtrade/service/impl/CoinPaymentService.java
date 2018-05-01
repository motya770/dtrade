package com.dtrade.service.impl;

import com.dtrade.service.ICoinPaymentService;
import com.sun.tools.javac.util.List;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;

//@Service
public class CoinPaymentService implements ICoinPaymentService {

   // private RestTemplate restTemplate = new RestTemplate();

    //template
    /*
     "curl --request POST \
             --url https://www.coinpayments.net/api.php \
            --header 'Content-Type: application/x-www-form-urlencoded' \
            --header 'HMAC: your_generated_hmac' \
            --data 'version=1&cmd=rates&key=your_api_public_key&format=json'"*/

    //@Override
    public static void login() {
        RestTemplate restTemplate = new RestTemplate();

        String privateKey = "101a03C22c7864f6e8d52724B2A9Ccd495795CB6c45324a80a9EDf55e2A0fbF6";
        String publicKey = "0620c2e54f0fe72ca283b949d20b01089afcd225d7463283c2812ffc26d96402";

        RequestEntity<String> entity = null;
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.put("Content-Type", List.of("application/x-www-form-urlencoded"));
            String body = "version=1&cmd=rates&key=" + publicKey + "&format=json";

            String hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, privateKey).hmacHex(body);
            headers.put("HMAC", List.of(hmac));
            entity = new RequestEntity<>(body, headers, HttpMethod.POST,
                      new URI("https://www.coinpayments.net/api.php"));

        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<String> responce  = restTemplate.exchange(entity, String.class);

        System.out.println(responce);
    }

    @Override
    public void deposit() {

    }

    @Override
    public void withdraw() {

    }

    public static void main(String... args){
        login();
    }
}
