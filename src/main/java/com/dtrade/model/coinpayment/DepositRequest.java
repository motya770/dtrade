package com.dtrade.model.coinpayment;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Embeddable
public class DepositRequest {

    private String ipn_version;
    private String ipn_type;
    private String ipn_mode;

    @NotEmpty
    private String ipnId;

    private String merchant;
    private String address;
    private String transactionId;

    @NotNull
    private Integer status;

    private String status_text;

    private String confirms;

    @NotEmpty
    private String email;

    @NotEmpty
    private String currencyUsd;

    @NotEmpty
    private String currencyCoin;

    @NotNull
    private BigDecimal amountUsd;

    @NotNull
    private BigDecimal amountCoin;

    public static DepositRequest build(Map<String,String> params){

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

        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setIpn_version(ipn_version);
        depositRequest.setIpn_type(ipn_type);
        depositRequest.setIpn_mode(ipn_mode);
        depositRequest.setIpnId(ipn_id);
        depositRequest.setMerchant(merchant);
        depositRequest.setAddress(address);
        depositRequest.setTransactionId(txn_id);
        depositRequest.setStatus(status);
        depositRequest.setCurrencyUsd(currencyUsd);
        depositRequest.setCurrencyCoin(currencyCoin);
        depositRequest.setStatus_text(status_text);
        depositRequest.setConfirms(confirms);
        depositRequest.setAmountUsd(amountUsd);
        depositRequest.setAmountCoin(amountCoin);
        depositRequest.setEmail(email);
        return depositRequest;
    }
}
