package com.dtrade.model.coinpayment;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    //private String currency;
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
}
