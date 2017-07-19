package com.dtrade.model.tradeorder;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
@Data
@Entity
public class TradeOrder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Diamond diamond;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Account account;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long creationDate;

    private Long executionDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TraderOrderStatus traderOrderStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TradeOrderType tradeOrderType;

}