package com.dtrade.model.coinpayment;


import com.dtrade.model.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "externalId_unique", columnNames = "externalId"))
public class CoinPayment {

    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    private String externalId;

    @NotNull
    private Long creationDate;

    @NotNull
    private CoinPaymentStatus coinPaymentStatus;

    private boolean balanceUpdated;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Account account;

    private BigDecimal amount;

    private String ipn_version;
    private String ipn_type;
    private String ipn_mode;
    private String ipn_id;
    private String merchant;
    private String address;
    private String txn_id;
    private String status;
    private String status_text;
    private String currency;
    private String confirms;
}
