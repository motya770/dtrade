package com.dtrade.model.coinpayment;


import com.dtrade.model.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@ToString
@Data
@Entity
@Table
public class CoinPayment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @Id
    private Long id;

    @NotNull
    private Long creationDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CoinPaymentStatus coinPaymentStatus;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Account account;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CoinPaymentType coinPaymentType;

    @Embedded
    private DepositRequest depositRequest;

    @Embedded
    private InWithdrawRequest inWithdrawRequest;


}
