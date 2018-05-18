package com.dtrade.model.coinpayment;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Embeddable
public class CoinPaymentRequest {

    private String ipn_version;
    private String ipn_type;
    private String ipn_mode;

    @NotEmpty
    private String ipnId;

    private String merchant;
    private String address;
    private String txn_id;

    @NotNull
    private Integer status;

    private String status_text;
    private String currency;
    private String confirms;

    @NotNull
    private BigDecimal amount;

    @NotEmpty
    private String email;
}
