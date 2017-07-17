package com.dtrade.model.stock;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    private Diamond diamond;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @ManyToOne
    private Account account;
}
