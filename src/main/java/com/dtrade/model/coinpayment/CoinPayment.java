package com.dtrade.model.coinpayment;


import com.dtrade.model.account.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

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
    private WithdrawRequest withdrawRequest;
}
