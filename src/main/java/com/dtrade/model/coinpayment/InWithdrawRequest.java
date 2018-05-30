package com.dtrade.model.coinpayment;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Embeddable
public class InWithdrawRequest {

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

    @Column(name = "in_w_amount_coin")
    @NotNull
    private BigDecimal amountCoin;

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
        String status_text = params.get("status");
        //TODO check transaction id;

        InWithdrawRequest request= new InWithdrawRequest();
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

        return request;
    }
}
