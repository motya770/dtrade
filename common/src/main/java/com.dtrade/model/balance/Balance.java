package com.dtrade.model.balance;


import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.currency.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@ToString
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(uniqueConstraints=
        @UniqueConstraint(columnNames={"currency", "account_id"})
)
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Account account;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(precision=19, scale=8)
    private BigDecimal amount;

    @Column(precision=19, scale=8)
    private BigDecimal frozen;

    @NotNull
    @Column(precision=19, scale=8)
    private BigDecimal open;

    @Column(columnDefinition = "boolean default true")
    private boolean baseBalance;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "balance")
    private List<BalanceActivity> balanceActivities;

    public BigDecimal getActualBalance(){
        return  amount.subtract(frozen).subtract(open);
    }

    public BalanceDTO getDTO(){

        BalanceDTO dto = new BalanceDTO();
        dto.setBalance(getActualBalance());
        return dto;
    }
}
