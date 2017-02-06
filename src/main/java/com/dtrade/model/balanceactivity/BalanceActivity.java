package com.dtrade.model.balanceactivity;

import com.dtrade.model.account.Account;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 12/3/16.
 */

@Entity
@Data
public class BalanceActivity {

    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BalanceActivityType balanceActivityType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Long date;



}
