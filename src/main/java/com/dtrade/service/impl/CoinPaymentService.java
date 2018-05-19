package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.CoinPaymentRequest;
import com.dtrade.model.coinpayment.CoinPaymentStatus;
import com.dtrade.repository.coinpayment.CoinPaymentRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.ICoinPaymentService;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CoinPaymentService.class);

    @Autowired
    private CoinPaymentRepository coinPaymentRepository;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IAccountService accountService;

    @Override
    public void proceed(CoinPaymentRequest coinPaymentRequest) {

       //Pay attention transaction has many coinPaymentRequest (in there system)

       CoinPayment coinPayment =  coinPaymentRepository.findByTransactionId(coinPaymentRequest.getTransactionId());
       if(coinPayment==null){
            coinPayment = create( coinPaymentRequest);
       }else{
            coinPayment.setCoinPaymentRequest(coinPaymentRequest);
       }

       if(coinPayment.getCoinPaymentStatus().equals(CoinPaymentStatus.CONFIRMED)){
            logger.debug("CoinPayment already confirmed {}", coinPayment.getCoinPaymentRequest().getIpnId());
            return;
       }

       Integer status =  coinPayment.getCoinPaymentRequest().getStatus();
       if(status==null){
           throw new TradeException("Status is not defined: " + coinPayment.getCoinPaymentRequest().getIpnId());
       }

       logger.debug("Status is {} for {}", status, coinPayment.getCoinPaymentRequest().getIpnId());
       if(status==100){
           confirmPayment(coinPayment);
       }
    }

    @Override
    public Page<CoinPayment> getAllByAccount(Account account) {
        return coinPaymentRepository.findByAccount(account, new PageRequest(0, 10));
    }

    @Override
    public CoinPayment findByExternalId(String transactionId) {
        return coinPaymentRepository.findByTransactionId(transactionId);
    }

    @Override
    public CoinPayment confirmPayment(CoinPayment coinPayment) {

        if(coinPayment.getCoinPaymentStatus().equals(CoinPaymentStatus.CONFIRMED)){
            throw new TradeException("Already confirmed");
        }

        balanceActivityService.createDepositBalanceActivity(coinPayment);

        coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CONFIRMED);
        return coinPaymentRepository.save(coinPayment);
    }

    @Override
    public CoinPayment create( CoinPaymentRequest coinPaymentRequest) {

        if(StringUtils.isEmpty(coinPaymentRequest.getIpnId())){
            throw new TradeException("IpnId is empty");
        }

        Account account = accountService.findByMail(coinPaymentRequest.getEmail());
        if(account==null){
            throw new TradeException("Account is empty: " + coinPaymentRequest.getEmail());
        }

        CoinPayment coinPayment= new CoinPayment();
        coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CREATED);
        coinPayment.setCreationDate(System.currentTimeMillis());
        coinPayment.setAccount(account);
        coinPayment.setCoinPaymentRequest(coinPaymentRequest);
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
        //deposit();
    }
}
