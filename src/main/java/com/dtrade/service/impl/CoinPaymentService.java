package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.*;
import com.dtrade.repository.coinpayment.CoinPaymentRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.ICoinPaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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
    public CoinPayment sendWithdraw(InWithdrawRequest withdrawRequest) {

        Account account = accountService.getStrictlyLoggedAccount();
        account = accountService.find(account.getId());

        BigDecimal amount = withdrawRequest.getAmountUsd();
        BigDecimal actualBalance = account.getBalance().add(account.getFrozenBalance());

        if(actualBalance.compareTo(amount) < 0){
            throw new TradeException("Account " + account.getId() + " don't have enough money for withdraw.");
        }

        accountService.freezeAmount(account, amount);

        withdrawRequest  = sendWithdrawRequest(withdrawRequest);

        CoinPayment coinPayment = createWithdraw(withdrawRequest);

        return coinPayment;
    }

    private InWithdrawRequest sendWithdrawRequest(InWithdrawRequest withdrawRequest){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> entity = null;
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.put("Content-Type", Collections.singletonList("application/x-www-form-urlencoded"));
            String body = "currency=" + withdrawRequest.getCurrencyCoin() +
                    "&currency2="  + withdrawRequest.getCurrencyUsd() + "&version=1&cmd=create_withdrawal&key=" + publicKey+
                    "&amount=" + withdrawRequest.getAmountUsd()
                    + "&format=json" + "&address="+ withdrawRequest.getAddress();

            String hmac = HmacUtils.hmacSha512Hex(privateKey, body);
            headers.put("HMAC", Collections.singletonList(hmac));
            entity = new RequestEntity<>(body, headers, HttpMethod.POST,
                    new URI("https://www.coinpayments.net/api.php"));

        }catch(Exception e){
            logger.error("{}", e);
            e.printStackTrace();
        }

        ResponseEntity<String> response  = restTemplate.exchange(entity, String.class);

        String result = response.getBody();
        if(!result.contains("ok")){
            throw new TradeException(result);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
            actualObj  = mapper.readTree(result);
        }catch (Exception e){
            logger.error("{}", e);
        }

        String id = actualObj.get("result").get("id").asText();
        Integer status = actualObj.get("result").get("status").asInt();
        BigDecimal amountCoin = new BigDecimal(actualObj.get("result").get("status").asText());

        System.out.println("id: " + id + " status: " + status + " amountCoin:" + amountCoin);

        withdrawRequest.setId(id);
        withdrawRequest.setStatus(status);
        withdrawRequest.setAmountCoin(amountCoin);

        //{"error":"ok","result":{"id":"CWCE4YN0MICCWCYAETRQQQK92L","status":0,"amount":"0.00365765"}},

        return withdrawRequest;
    }

    @Override
    public CoinPayment createWithdraw(InWithdrawRequest withdrawRequest){
        Account account = accountService.getStrictlyLoggedAccount();
        CoinPayment coinPayment= new CoinPayment();
        coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CREATED);
        coinPayment.setCreationDate(System.currentTimeMillis());
        coinPayment.setAccount(account);
        coinPayment.setCoinPaymentType(CoinPaymentType.WITHDRAW);
        coinPayment.setInWithdrawRequest(withdrawRequest);
        coinPayment = coinPaymentRepository.save(coinPayment);
        return coinPayment;
    }

    @Override
    public void proceedWithdraw(InWithdrawRequest withdrawRequest) {

        CoinPayment coinPayment = coinPaymentRepository.findInWithdrawById(withdrawRequest.getId());
        if(coinPayment==null){
            throw new TradeException("CoinPayment for this withdraw is not found (id: |" + withdrawRequest.getId() + ")");
        }

        coinPayment.setInWithdrawRequest(withdrawRequest);

        coinPaymentRepository.save(coinPayment);

        if(coinPayment.getCoinPaymentStatus().equals(CoinPaymentStatus.CONFIRMED)){
            logger.debug("CoinPayment already confirmed {}", coinPayment.getInWithdrawRequest().getIpnId());
            return;
        }

        Integer status =  coinPayment.getInWithdrawRequest().getStatus();
        if(status==null){
            throw new TradeException("Status is not defined: " + coinPayment.getInWithdrawRequest().getIpnId());
        }

        logger.debug("Status is {} for {}", status, coinPayment.getInWithdrawRequest().getIpnId());
        if(status==100){
            confirmWithdraw(coinPayment);
        }
    }

    //receive money
    @Override
    public void proceedDeposit(DepositRequest depositRequest) {

       //Pay attention transaction has many depositRequest (in there system)

       CoinPayment coinPayment = coinPaymentRepository.findDepositByIpnId(depositRequest.getIpnId());
       //TODO check
       //coinPaymentRepository.findByTransactionId(depositRequest.getTransactionId());

       if(coinPayment==null){
            coinPayment = createDeposit(depositRequest);
       }else{
            coinPayment.setDepositRequest(depositRequest);
       }

       if(coinPayment.getCoinPaymentStatus().equals(CoinPaymentStatus.CONFIRMED)){
            logger.debug("CoinPayment already confirmed {}", coinPayment.getDepositRequest().getIpnId());
            return;
       }

       Integer status =  coinPayment.getDepositRequest().getStatus();
       if(status==null){
           throw new TradeException("Status is not defined: " + coinPayment.getDepositRequest().getIpnId());
       }

       logger.debug("Status is {} for {}", status, coinPayment.getDepositRequest().getIpnId());
       if(status==100){
           confirmPayment(coinPayment);
       }
    }

    @Override
    public void checkHmac(String hmac, byte[] body) {
        if(StringUtils.isEmpty(hmac) || StringUtils.isEmpty(body)){
            throw new TradeException("Hmac or body is empty");
        }

        String calculatedHmac =  HmacUtils.hmacSha512Hex(privateKey.getBytes(), body);

        calculatedHmac = calculatedHmac.trim();
        hmac = hmac.trim();

        System.out.println("you hmac: " + hmac);
        System.out.println("calculatedHmac: " + calculatedHmac);

        //TODO fix check IMPORTANT
        if(true){
            return;
        }

        if(!calculatedHmac.equals(hmac)){
            throw new TradeException("Hmac is not equals");
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

    private void checkConfirmed(CoinPayment coinPayment){
        if(coinPayment.getCoinPaymentStatus().equals(CoinPaymentStatus.CONFIRMED)){
            throw new TradeException("Already confirmed");
        }
    }

    @Override
    public CoinPayment confirmWithdraw(CoinPayment coinPayment) {
        checkConfirmed(coinPayment);
        balanceActivityService.createWithdrawBalanceActivity(coinPayment);
        return confirm(coinPayment);
    }

    private CoinPayment confirm(CoinPayment coinPayment){
        coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CONFIRMED);
        return coinPaymentRepository.save(coinPayment);
    }

    @Override
    public CoinPayment confirmPayment(CoinPayment coinPayment) {
        checkConfirmed(coinPayment);
        balanceActivityService.createDepositBalanceActivity(coinPayment);
        return confirm(coinPayment);
    }

    @Override
    public CoinPayment createDeposit(DepositRequest depositRequest) {

        if(StringUtils.isEmpty(depositRequest.getIpnId())){
            throw new TradeException("IpnId is empty");
        }

        Account account = accountService.findByMail(depositRequest.getEmail());
        if(account==null){
            throw new TradeException("Account is empty: " + depositRequest.getEmail());
        }

        CoinPayment coinPayment= new CoinPayment();
        coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CREATED);
        coinPayment.setCreationDate(System.currentTimeMillis());
        coinPayment.setAccount(account);
        coinPayment.setCoinPaymentType(CoinPaymentType.DEPOSIT);
        coinPayment.setDepositRequest(depositRequest);
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

    public static void main(String... args) throws Exception{

       // you hmac: d7256fe01b4c64f230fa00132ee10fd423ddfeee139f046b730e95d0c3b4c794f81bca3f6d987a6bbe75630b0683a6c5812e3cc6dcf37205282e135dac1fd69f
       // app_1         | calculatedHmac: ��U�A�]�hUu��߮!�EK����X�;c,5�˲�B�l�(�"�&�U5��-�z�
       // app_1         | 2018-05-31 23:51:05.216 ERROR b49edc35a336 --- [io-8080-exec-33] c.d.s.i.TradeOrderService                : Type button is unknown

        //ceb65e9f035836b7477b2197d8bb8bc08f52515062d91e918c7ba097227d0b983bddbc6616fa00cf7538bb785297d433bd0a76c3355e3ffc4558d05b77b25f46
        String body = "amount1=5&amount2=0.0087&currency1=USD&currency2=ETH&email=matvei.kudelin%40gmail.com&fee=4.0E-5&first_name=k&ipn_id=5e5ce1070495c6e423f197b2ef60d36c&ipn_mode=hmac&ipn_type=button&ipn_version=1.0&item_amount=5&item_name=Diaminds+Deposit&last_name=1&merchant=1fb3cd572acffff43b1c0356d5429f1c&quantity=1&received_amount=0&received_confirms=0&shipping=0&status=0&status_text=Waiting+for+buyer+funds...&subtotal=5&tax=0&txn_id=CPCE3JKDUUWVP299HSX9R2UQYR";

        String calculatedHmac =  HmacUtils.hmacSha512Hex(privateKey, body);
        System.out.println(calculatedHmac);

    }
}
