package com.dtrade.model.balanceactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.tradeorder.TradeOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BalanceActivityType balanceActivityType;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal balanceSnapshot;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private TradeOrder buyOrder;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private TradeOrder sellOrder;

    @NotNull
    private Long createDate;

}
