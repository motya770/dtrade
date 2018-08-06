package com.dtrade.model.coinpayment;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@ToString
@Data
@Embeddable
public class InWithdrawRequest {

    private static final Logger logger = LoggerFactory.getLogger(InWithdrawRequest.class);

    @Column(name = "in_w_id")
    @NotEmpty
    private String id;

    @Column(name = "in_w_ipn_version")
    private String ipn_version;

    @Column(name = "in_w_ipn_type")
    private String ipn_type;

    @Column(name = "in_w_ipn_mode")
    private String ipn_mode;

    @Column(name = "in_w_ipn_id")
    @NotEmpty
    private String ipnId;

    @Column(name = "in_w_merchant")
    private String merchant;

    @Column(name = "in_w_address")
    private String address;

    @Column(name = "in_w_transaction_id")
    private String transactionId;

    @Column(name = "in_w_status")
    @NotNull
    private Integer status;

    @Column(name = "in_w_status_texts")
    private String status_text;

    @Column(name = "in_w_currency_usd")
    @NotEmpty
    private String currencyUsd;

    @Column(name = "in_w_currency_coin")
    @NotEmpty
    private String currencyCoin;

    @Column(name = "in_w_amount_usd")
    @NotNull
    private BigDecimal amountUsd;

    @Column(name = "in_w_amount_coin", precision = 18, scale = 8)
    @NotNull
    private BigDecimal amountCoin;

    public static InWithdrawRequest initiliazeRequest(String currencyCoin,
                                                      String address, String amountCoin){
            InWithdrawRequest request = new InWithdrawRequest();
            request.setCurrencyCoin(currencyCoin);
            //request.setCurrencyUsd(currencyUsd);
            request.setAddress(address);
            request.setAmountCoin(new BigDecimal(amountCoin));
            //request.setAmountUsd(new BigDecimal(amountUsd));
            return request;
    }

    public static InWithdrawRequest build(Map<String, String> params){
        /*
                        K:address V:0x10D75F90b0F483942aDd5a947b71D8617BB012eD
        app_1         | K:amount V:0.00876516
        app_1         | K:amounti V:876516
        app_1         | K:currency V:ETH
        app_1         | K:id V:CWCE5VTG0P9HAQ77GUMI94PNDD
        app_1         | K:ipn_id V:177f09c0ef621c271a049ff01785a627
        app_1         | K:ipn_mode V:hmac
        app_1         | K:ipn_type V:withdrawal
        app_1         | K:ipn_version V:1.0
        app_1         | K:merchant V:1fb3cd572acffff43b1c0356d5429f1c
        app_1         | K:status V:2
        app_1         | K:status_text V:Complete
        app_1         | K:txn_id V:0x4a025ceece1f4b23972b5c0d56b4638682fc263e1b49ae3debe2671b767cfaa6
*/

        /*

              {"id":"CWCF4GUHUFJIFLBPS6EBCZDNGD",
                "ipn_version":null,
                "ipn_type":null,"ipn_mode":null,
                "ipnId":null,"merchant":null,
                "address":"0x10D75F90b0F483942aDd5a947b71D8617BB012eD",
                "transactionId":null,"status":0,"status_text":null,
                "currencyUsd":"USD",
                "currencyCoin":"ETH",
                "amountUsd":2,
                "amountCoin":0}
                */

        //{"id":"CWCF5HKGVVM7BL8YV9ODD1HJGC","ipn_version":
        // "withdrawal","ipn_type":"1.0","ipn_mode":"hmac",
        // "ipnId":"1e02cc489e4d25ec24bca56234a5b9a9","merchant":"1fb3cd572acffff43b1c0356d5429f1c",
        // "address":"0x10D75F90b0F483942aDd5a947b71D8617BB012eD",
        // "transactionId":"0x77c8aba9e2f184102ed8e1899708f0da0d1f055b1b3981922009f458f7e84332","status":2,
        // "status_text":"Complete","currencyUsd":null,"currencyCoin":"ETH","amountUsd":null,"amountCoin":0}

        String id = params.get("id");
        String address = params.get("address");
        BigDecimal amountCoin = new BigDecimal(params.get("amount"));
        String currencyCoin =  params.get("currency");
        String txn_id = params.get("txn_id");
        String ipn_id = params.get("ipn_id");
        String ipn_mode = params.get("ipn_mode");
        String ipn_type = params.get("ipn_version");
        String ipn_version = params.get("ipn_type");
        String merchant = params.get("merchant");
        Integer status =  Integer.parseInt(params.get("status"));
        String status_text = params.get("status_text");

        InWithdrawRequest request= new InWithdrawRequest();
        request.setId(id);
        request.setAddress(address);
        request.setAmountCoin(amountCoin);
        request.setCurrencyCoin(currencyCoin);
        request.setTransactionId(txn_id);
        request.setIpnId(ipn_id);
        request.setIpn_mode(ipn_mode);
        request.setIpn_type(ipn_type);
        request.setIpn_version(ipn_version);
        request.setMerchant(merchant);
        request.setStatus(status);
        request.setStatus_text(status_text);

        logger.info("Withdraw request built {}", request);

        return request;
    }

    public void update(InWithdrawRequest newRequest){
        this.setId(newRequest.getId());
        this.setAddress(newRequest.getAddress());
        this.setAmountCoin(newRequest.getAmountCoin());
        this.setCurrencyCoin(newRequest.getCurrencyCoin());
        this.setTransactionId(newRequest.getTransactionId());
        this.setIpnId(newRequest.getIpnId());
        this.setIpn_mode(newRequest.getIpn_mode());
        this.setIpn_type(newRequest.getIpn_type());
        this.setIpn_version(newRequest.getIpn_version());
        this.setMerchant(newRequest.getMerchant());
        this.setStatus(newRequest.getStatus());
        this.setStatus_text(newRequest.getStatus_text());

        /*
        {"id":"CWCF35RVEVGCRTRM2SSNGITBEA",
                "ipn_version":"withdrawal","ipn_type":"1.0",
                "ipn_mode":"hmac","ipnId":"26c45b6b40fe26ff2f0f87ff7d8e6182",
                "merchant":"1fb3cd572acffff43b1c0356d5429f1c",
                "address":"0x10D75F90b0F483942aDd5a947b71D8617BB012eD",
                "transactionId":"0x0bfbd91a4a99f1f567101da3a7395a80e6867e0a6ff83130c51f6902d620d631",
                "status":2,"status_text":"Complete","currencyUsd":null,"currencyCoin":"ETH","amountUsd":null,"amountCoin":0}
                */
        logger.info("Withdraw request update {}", this);
    }

    public static void main(String... args){
        String amount = "0.00876516";
        BigDecimal bigDecimal = new BigDecimal(amount);
        System.out.println(bigDecimal);
    }
}
