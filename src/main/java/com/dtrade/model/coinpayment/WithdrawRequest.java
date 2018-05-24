package com.dtrade.model.coinpayment;


import lombok.Data;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Data
@Embeddable
public class WithdrawRequest {
    private String cmd;
    private BigDecimal amount;
    private Integer add_tx_fee;
    private String currencyCoin;
    private String currencyFiat;
    private String address;
    private String dest_tag;
    private String ipn_url;
    private Integer auto_confirm = 0;
    private String note;
}
