package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.Const;
import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.*;
import com.dtrade.model.currency.Currency;
import com.dtrade.repository.coinpayment.CoinPaymentRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IBalanceService;
import com.dtrade.service.ICoinPaymentService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;

@Transactional
@Service
public class CoinPaymentService implements ICoinPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(CoinPaymentService.class);

    @Autowired
    private CoinPaymentRepository coinPaymentRepository;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceService balanceService;

    @Override
    public CoinPayment sendWithdraw(InWithdrawRequest withdrawRequest) {

        Account account = accountService.getStrictlyLoggedAccount();
        account = accountService.find(account.getId());

        Currency currency = Currency.valueOf(withdrawRequest.getCurrencyCoin());

        BigDecimal amount = withdrawRequest.getAmountCoin();
        BigDecimal actualBalance = balanceService.getBalance(currency, account).getAmount();

        if(actualBalance.compareTo(amount) < 0){
            throw new TradeException("Account " + account.getId() + " don't have enough money for withdraw.");
        }

        balanceService.freezeAmount(currency, account, amount);

        withdrawRequest  = sendWithdrawRequest(withdrawRequest);

        CoinPayment coinPayment = createWithdraw(withdrawRequest);
        return coinPayment;
    }

    /*
    private void poolDeposit(String transactionId){
        String body = "&version=1&cmd=get_tx_info&key=" + publicKey+
                "&txid=" + transactionId
                + "&format=json";
        String result  = requestServer(body);
       logger.info("deposit response: " + result);

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

        logger.info(" {}");
        Long timeCreated = actualObj.get("result").get("time_created").asLong();
        Long timeExpires = actualObj.get("result").get("time_expires").asLong();
        Integer status = actualObj.get("result").get("status").asInt();
        String statusText = actualObj.get("result").get("status_text").asText();
        BigDecimal amount =new BigDecimal(actualObj.get("result").get("amount").asText());
        Integer confirmed =actualObj.get("result").get("recv_confirms").asInt();
        String address =actualObj.get("result").get("payment_address").asText();
        Long  timeCompleted =actualObj.get("result").get("time_completed").asLong();
        String currencyCoin =actualObj.get("result").get("coin").asText();

        //DepositRequest.build()

        {"error":"ok","result":
            {"time_created":1527935482,
            "time_expires":1528021882,
                    "status":100,
                    "status_text":"Complete",
                    "type":"coins",
                    "coin":"ETH"
                    "amount":337000,"amountf":"0.00337000",
                    "received":337000,"receivedf":"0.00337000","recv_confirms":3,
                    "payment_address":"0x46cd27c57e8d41f80142c3fcf98c63cacd16c7d5","time_completed":1527936185}}

    }
    */

    private String requestServer(String body){
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> entity = null;
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.put("Content-Type", Collections.singletonList("application/x-www-form-urlencoded"));
            String hmac = HmacUtils.hmacSha512Hex(privateKey, body);
            headers.put("HMAC", Collections.singletonList(hmac));
            entity = new RequestEntity<>(body, headers, HttpMethod.POST,
                    new URI("https://www.coinpayments.net/api.php"));

        }catch(Exception e){
            logger.error("{}", e);
            e.printStackTrace();
        }
       ResponseEntity<String> response  = restTemplate.exchange(entity, String.class);
       return response.getBody();
    }

    /*
    private void poolWithdraw(CoinPayment coinPayment){

        InWithdrawRequest withdrawRequest = coinPayment.getInWithdrawRequest();
        String body = "&version=1&cmd=get_withdrawal_info&key=" + publicKey+
                "&id=" + withdrawRequest.getId()
                + "&format=json";
        String result = requestServer(body);
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


                "time_created":1391924372,
                "status":2,
                "status_text":"Complete",
                "coin":"BTC",
                "amount":40000000,
                "amountf":"0.40000000",
                "send_address":"1BitcoinAddress",
                "send_txid":"hex_txid"

        logger.info(" {}");
        Integer status = actualObj.get("result").get("status").asInt();
        String statusText = actualObj.get("result").get("status_text").asText();
        String currencyCoin = actualObj.get("result").get("coin").asText();
        BigDecimal amountCoin = new BigDecimal(actualObj.get("result").get("amountf").asText());
        String address =actualObj.get("result").get("send_address").asText();
        String transactionId =actualObj.get("result").get("send_txid").asText();

        withdrawRequest.setStatus(status);
        withdrawRequest.setStatus_text(statusText);
        withdrawRequest.setCurrencyCoin(currencyCoin);
        withdrawRequest.setAmountCoin(amountCoin);
        withdrawRequest.setAddress(address);
        withdrawRequest.setTransactionId(transactionId);

        coinPayment = coinPaymentRepository.save(coinPayment);

        proceedWithdraw(coinPayment);
    }*/

    private InWithdrawRequest sendWithdrawRequest(InWithdrawRequest withdrawRequest){

        /*
         String body = "currency=" + withdrawRequest.getCurrencyCoin() +
                "&currency2="  + withdrawRequest.getCurrencyUsd() + "&version=1&cmd=create_withdrawal&key=" + publicKey+
                "&amount=" + withdrawRequest.getAmountUsd()
                + "&format=json" + "&address="+ withdrawRequest.getAddress();
        */

        String body = "currency=" + withdrawRequest.getCurrencyCoin() +
                "&version=1&cmd=create_withdrawal&key=" + publicKey+
                "&amount=" + withdrawRequest.getAmountCoin()
                + "&format=json" + "&address="+ withdrawRequest.getAddress();

        String result = requestServer(body);
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
        BigDecimal amountCoin = new BigDecimal(actualObj.get("result").get("amount").asText());

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
        System.out.println("1. Proceed: " + withdrawRequest);

        CoinPayment coinPayment = coinPaymentRepository.findInWithdrawById(withdrawRequest.getId());

        if(coinPayment.getCoinPaymentStatus().equals(CoinPaymentStatus.CONFIRMED)){
            logger.error("CoinPayment already confirmed {}", coinPayment.getInWithdrawRequest().getIpnId());
            return;
        }

        System.out.println("2. Proceed: " + coinPayment);

        Integer status =  withdrawRequest.getStatus();
        if(status==null){
            throw new TradeException("Status is not defined: " + coinPayment.getInWithdrawRequest().getIpnId());
        }


        System.out.println("3. Status: " + status);

        logger.debug("Status is {} for {}", status, coinPayment.getInWithdrawRequest().getIpnId());

        coinPayment.getInWithdrawRequest().update(withdrawRequest);


        System.out.println("4. Updated: " + coinPayment.getInWithdrawRequest());

        if(status == 0){
            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.WAITING_FOR_EMAIL);
        }else if(status==1){
            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.PENDING);
        }else if(status==2) {
            confirmWithdraw(coinPayment);
        }else {
            throw new TradeException("Unknown withdraw status " + status);
        }

        System.out.println("5. New status: " + coinPayment);

        coinPaymentRepository.save(coinPayment);
    }

    private void updateStatus(CoinPayment coinPayment, Integer status){
        if(status == -2){

            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.REFUND);

        }else if (status == -1){

            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.CANCELED);

        }else if(status == 0){

            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.WAITING_FOR_FUNDS);

        }else if(status == 1){

            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.COIN_RECEIVED);

        }else if(status == 2){

            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.QUEUED_FOR_NIGHT);

        }else if(status == 3){

            coinPayment.setCoinPaymentStatus(CoinPaymentStatus.PENDING);

        }else if (status==100){
            //update status later
        }else {
            throw new TradeException("Status is not in the range: " + status);
        }

        coinPaymentRepository.save(coinPayment);
    }


    /*
    @Override
    public void requestWithdraw(String id){
        CoinPayment coinPayment = coinPaymentRepository.findInWithdrawById(id);
        poolWithdraw(coinPayment);
    }

    @Override
    public void requestDeposit(String transactionId){
        poolDeposit(transactionId);
    }*/

    @Override
    public Page<CoinPayment> findAll(Integer pageNumber) {
        if(pageNumber==null){
            pageNumber = 0;
        }
        return coinPaymentRepository.findAll(PageRequest.of(pageNumber, 20, Sort.Direction.DESC, Const.F_ID));
    }

    //receive money
    @Override
    public void proceedDeposit(DepositRequest depositRequest) {

       //Pay attention transaction has many depositRequest (in there system)
       CoinPayment coinPayment = coinPaymentRepository.findDepositByTransactionId(depositRequest.getTransactionId());

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
       updateStatus(coinPayment, status);

       if(status==100){
           confirmPayment(coinPayment);
       }
    }

    @Override
    public void checkHmac(String hmac, String body) {
        if(StringUtils.isEmpty(hmac) || StringUtils.isEmpty(body)){
            throw new TradeException("Hmac or body is empty");
        }

        String calculatedHmac =  HmacUtils.hmacSha512Hex(privateKeyIpn, body);

        calculatedHmac = calculatedHmac.trim();
        hmac = hmac.trim();

        System.out.println("you hmac: " + hmac);
        System.out.println("calculatedHmac: " + calculatedHmac);

        if(!calculatedHmac.equals(hmac)){
            throw new TradeException("Hmac is not equals");
        }
    }

    @Override
    public Page<CoinPayment> getAllByAccount(Account account) {
        return coinPaymentRepository.findByAccount(account, new PageRequest(0, 10, new Sort(
                new Sort.Order(Sort.Direction.DESC, "creationDate"))));
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

   private static String privateKeyIpn = "wekjqijw7834yfr98y43jfv";
   private static String privateKey = "101a03C22c7864f6e8d52724B2A9Ccd495795CB6c45324a80a9EDf55e2A0fbF6";
   private static String publicKey = "0620c2e54f0fe72ca283b949d20b01089afcd225d7463283c2812ffc26d96402";

    //private static String privateKey =  "Fb695eB4da60bC40BF38F5bab20f5734D1808Fb5155f99CfFCb87F16D51cf18e";
    //private static String publicKey = "f9ddad523eee45368a45a62fbf8858002f90ff1f7b608cd2506e5334e05ff664";


    public static void main(String... args){


        String body = "&version=1&cmd=get_tx_info&key=" + publicKey+
                "&txid=" + "CPCF57LPWJWW4MRRQMJNG92B6V"
                + "&format=json";

        /*
        String body = "&version=1&cmd=get_deposit_address&key=" + publicKey+
                "&currency=" + "ETH"
                + "&format=json";*/

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<String> entity = null;
        try{

            HttpHeaders headers = new HttpHeaders();
            headers.put("Content-Type", Collections.singletonList("application/x-www-form-urlencoded"));
            String hmac = HmacUtils.hmacSha512Hex(privateKey, body);
            headers.put("HMAC", Collections.singletonList(hmac));
            entity = new RequestEntity<>(body, headers, HttpMethod.POST,
                    new URI("https://www.coinpayments.net/api.php"));

        }catch(Exception e){
            logger.error("{}", e);
            e.printStackTrace();
        }
        /*{"error":"ok","result":{"time_created":1527935482,
        "time_expires":1528021882,
        "status":100,"status_text":"Complete","type":"coins","coin":"ETH",
        "amount":337000,"amountf":"0.00337000","received":337000,"receivedf":"0.00337000",
        "recv_confirms":3,"payment_address":"0x46cd27c57e8d41f80142c3fcf98c63cacd16c7d5","time_completed":1527936185}}
         */

        ResponseEntity<String> response  = restTemplate.exchange(entity, String.class);
        System.out.println(response.getBody());


    }

}
