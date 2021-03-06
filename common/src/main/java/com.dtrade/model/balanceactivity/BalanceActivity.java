package com.dtrade.model.balanceactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.tradeorder.TradeOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 12/3/16.
 */


@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Data
public class BalanceActivity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @Id
    private Long id;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BalanceActivityType balanceActivityType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Balance balance;

    @Column(precision=19, scale=8)
    private BigDecimal amount;

    @Column(precision=19, scale=8)
    private BigDecimal price;

    @Column(precision=19, scale=8)
    @NotNull
    private BigDecimal sum;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

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

    @Column(columnDefinition = "boolean default false")
    private boolean operationOnBaseCurrency;

    //@Version
    //private Long version;

    public String toString(){
        return "{ id: " + getId() + "}";
    }

}
