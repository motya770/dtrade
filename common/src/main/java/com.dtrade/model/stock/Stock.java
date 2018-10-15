package com.dtrade.model.stock;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
@Data
@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Diamond diamond;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    private BigDecimal stockInTrade;
}
