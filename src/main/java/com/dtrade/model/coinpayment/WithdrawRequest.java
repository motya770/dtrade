package com.dtrade.model.coinpayment;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Embeddable
public class WithdrawRequest {

    @Column(name = "withdraw_cmd")
    private String cmd;

    @Column(name = "withdraw_add_tx_fee")
    private Integer add_tx_fee;

    @Column(name = "withdraw_currency_coin")
    private String currencyCoin;

    @Column(name = "withdraw_currency_fiat")
    private String currencyFiat;

    @Column(name = "withdraw_address")
    private String address;

    @Column(name = "withdraw_dest_tag")
    private String dest_tag;

    @Column(name = "withdraw_ipn_url")
    private String ipn_url;

    @Column(name = "withdraw_auto_confirm")
    private Integer auto_confirm = 0;

    @Column(name = "withdraw_note")
    private String note;

    @Column(name = "withdraw_amount")
    private BigDecimal amount;

    public static WithdrawRequest build(String currencyCoin, String currencyFiat,
                                        String address, String amount){

       WithdrawRequest withdrawRequest = new WithdrawRequest();
       withdrawRequest.setCurrencyCoin(currencyCoin);
       withdrawRequest.setCurrencyFiat(currencyFiat);
       withdrawRequest.setAddress(address);
       withdrawRequest.setAmount(new BigDecimal(amount));
       return withdrawRequest;
    }
}
