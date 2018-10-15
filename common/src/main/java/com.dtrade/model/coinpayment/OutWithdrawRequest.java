package com.dtrade.model.coinpayment;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Embeddable
public class OutWithdrawRequest {

    @Column(name = "out_w_cmd")
    private String cmd;

    @Column(name = "out_w_add_tx_fee")
    private Integer add_tx_fee;

    @Column(name = "out_w_currency_coin")
    private String currencyCoin;

    @Column(name = "out_w_currency_fiat")
    private String currencyFiat;

    @Column(name = "out_w_address")
    private String address;

    @Column(name = "out_w_dest_tag")
    private String dest_tag;

    @Column(name = "out_w_ipn_url")
    private String ipn_url;

    @Column(name = "out_w_auto_confirm")
    private Integer auto_confirm = 0;

    @Column(name = "out_w_note")
    private String note;

    @Column(name = "out_w_amount")
    private BigDecimal amount;

    public static OutWithdrawRequest build(String currencyCoin, String currencyFiat,
                                           String address, String amount){

       OutWithdrawRequest outWithdrawRequest = new OutWithdrawRequest();
       outWithdrawRequest.setCurrencyCoin(currencyCoin);
       outWithdrawRequest.setCurrencyFiat(currencyFiat);
       outWithdrawRequest.setAddress(address);
       outWithdrawRequest.setAmount(new BigDecimal(amount));
       return outWithdrawRequest;
    }
}
