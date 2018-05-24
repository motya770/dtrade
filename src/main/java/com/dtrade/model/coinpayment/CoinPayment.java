package com.dtrade.model.coinpayment;


import com.dtrade.model.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table
public class CoinPayment {

    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    private Long creationDate;

    @NotNull
    private CoinPaymentStatus coinPaymentStatus;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Account account;

    @Embedded
    private DepositRequest depositRequest;

   // @Embedded
    //private WithdrawRequest withdrawRequest;
}
