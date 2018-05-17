package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.CoinPaymentStatus;
import com.dtrade.repository.coinpayment.CoinPaymentRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.ICoinPaymentService;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.Collections;

@Service
public class CoinPaymentService implements ICoinPaymentService {

    @Autowired
    private CoinPaymentRepository coinPaymentRepository;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IAccountService accountService;

    @Override
    public void proceed() {

    }

    @Override
    public Page<CoinPayment> getAllByAccount(Account account) {
        return coinPaymentRepository.findByAccount(account, new PageRequest(0, 10));
    }

    @Override
    public CoinPayment findByExternalId(String externalId) {
        return coinPaymentRepository.findByExternalId(externalId);
    }

    @Override
    public CoinPayment confirmPayment(String externalId) {

        CoinPayment coinPayment = findByExternalId(externalId);
        if(coinPayment.getCoinPaymentStatus().equals(CoinPaymentStatus.CONFIRMED)){
            throw new TradeException("Already confirmed");
        }

        if(coinPayment.isBalanceUpdated()){
            throw new TradeException("Balance already updated");
        }

        balanceActivityService.createDepositBalanceActivity(coinPayment);

        coinPayment.setBalanceUpdated(true);
        coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CONFIRMED);

        return coinPaymentRepository.save(coinPayment);
    }

    @Override
    public CoinPayment create(String login, String externalId) {

        if(StringUtils.isEmpty(externalId)){
            throw new TradeException("ExternalId is empty");
        }

        Account account = accountService.findByMail(login);
        if(account==null){
            throw new TradeException("Account is empty: " + login);
        }

        CoinPayment coinPayment= new CoinPayment();
        coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CREATED);
        coinPayment.setBalanceUpdated(false);
        coinPayment.setCreationDate(System.currentTimeMillis());
        coinPayment.setExternalId(externalId);
        coinPayment.setAccount(account);
        coinPayment = coinPaymentRepository.save(coinPayment);
        return coinPayment;
    }

    // private RestTemplate restTemplate = new RestTemplate();

    //template
    /*
     "curl --request POST \
             --url https://www.coinpayments.net/api.php \
            --header 'Content-Type: application/x-www-form-urlencoded' \
            --header 'HMAC: your_generated_hmac' \
            --data 'version=1&cmd=rates&key=your_api_public_key&format=json'"*/

    private static String privateKey = "101a03C22c7864f6e8d52724B2A9Ccd495795CB6c45324a80a9EDf55e2A0fbF6";
    private static String publicKey = "0620c2e54f0fe72ca283b949d20b01089afcd225d7463283c2812ffc26d96402";


    //@Override
    public static void login() {
        RestTemplate restTemplate = new RestTemplate();


        RequestEntity<String> entity = null;
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.put("Content-Type", Collections.singletonList("application/x-www-form-urlencoded"));
            String body = "version=1&cmd=rates&key=" + publicKey + "&format=json";

            String hmac = HmacUtils.hmacSha512Hex(privateKey, body);

            headers.put("HMAC", Collections.singletonList(hmac));
            entity = new RequestEntity<>(body, headers, HttpMethod.POST,
                      new URI("https://www.coinpayments.net/api.php"));

        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<String> responce  = restTemplate.exchange(entity, String.class);

        System.out.println(responce);
    }



    //@Override
    public static void deposit() {
        RestTemplate restTemplate = new RestTemplate();

        double amount=10000;
        String buyer_email = "motya770@gmail.com";

        RequestEntity<String> entity = null;
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.put("Content-Type", Collections.singletonList("application/x-www-form-urlencoded"));
            String body = "currency1=USD&currency2=BTC&version=1&cmd=create_transaction&key=" + publicKey+ "&amount=" + amount +
                    "&buyer_email=" + buyer_email + "&format=json";

            String hmac = HmacUtils.hmacSha512Hex(privateKey, body);
            headers.put("HMAC", Collections.singletonList(hmac));
            entity = new RequestEntity<>(body, headers, HttpMethod.POST,
                    new URI("https://www.coinpayments.net/api.php"));

        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<String> responce  = restTemplate.exchange(entity, String.class);
        System.out.println(responce);
    }

    //@Override
    public void withdraw() {

        RestTemplate restTemplate = new RestTemplate();

        double amount=10000;
        String buyer_email = "motya770@gmail.com";
        String address = "";

        RequestEntity<String> entity = null;
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.put("Content-Type", Collections.singletonList("application/x-www-form-urlencoded"));

            String body = "currency=BTC&currency2=USD&version=1&cmd=create_withdrawal&key=" + publicKey+ "&amount=" + amount +
                    "&buyer_email=" + buyer_email + "&format=json&address=" + address + "&=" ;

            String hmac = HmacUtils.hmacSha512Hex(privateKey, body);
            headers.put("HMAC", Collections.singletonList(hmac));
            entity = new RequestEntity<>(body, headers, HttpMethod.POST,
                    new URI("https://www.coinpayments.net/api.php"));

        }catch(Exception e){
            e.printStackTrace();
        }

        ResponseEntity<String> responce  = restTemplate.exchange(entity, String.class);
        System.out.println(responce);
    }

    public static void main(String... args){
        //login();
        deposit();
    }
}
