package com.dtrade.model.tradeorder;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by kudelin on 6/27/17.
 */
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
public class TradeOrder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Diamond diamond;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Account account;

    @Column(precision=19, scale=8)
    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal initialAmount;

    @Column(precision=19, scale=8)
    @NotNull
    private BigDecimal price;

    @NotNull
    private Long creationDate;

    private Long executionDate;

    @Column(precision=19, scale=8)
    @NotNull
    private BigDecimal executionSum;

    //BUY, SELL
    @Enumerated(EnumType.STRING)
    @NotNull
    private TradeOrderDirection tradeOrderDirection;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TradeOrderType tradeOrderType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TraderOrderStatus traderOrderStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TraderOrderStatusIndex traderOrderStatusIndex;


    public void setTraderOrderStatus(TraderOrderStatus status){
        this.traderOrderStatus = status;

        if(this.traderOrderStatus.equals(TraderOrderStatus.CREATED) ||
                traderOrderStatus.equals(TraderOrderStatus.IN_MARKET)){
            this.traderOrderStatusIndex = TraderOrderStatusIndex.LIVE;
        }

        if(this.traderOrderStatus.equals(TraderOrderStatus.EXECUTED) ||
                traderOrderStatus.equals(TraderOrderStatus.CANCELED) ||
                traderOrderStatus.equals(TraderOrderStatus.REJECTED)){
            this.traderOrderStatusIndex = TraderOrderStatusIndex.HISTORY;
        }
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof TradeOrder)) {
            return false;
        }
        TradeOrder order = (TradeOrder) o;
        return Objects.equals(id, order.getId());
    }

    /*
    @Override
    public int compareTo(Object o) {
        return id.compareTo(((TradeOrder)o).getId());
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(id, 1);
    }

    @Override
    public String toString() {
        return "TradeOrder{" +
                "id=" + id +
                ", amount=" + amount +
                ", initialAmount=" + initialAmount +
                ", price=" + price +
                ", creationDate=" + creationDate +
                ", executionDate=" + executionDate +
                ", executionSum=" + executionSum +
                ", tradeOrderDirection=" + tradeOrderDirection +
                ", tradeOrderType=" + tradeOrderType +
                ", traderOrderStatus=" + traderOrderStatus +
                ", traderOrderStatusIndex=" + traderOrderStatusIndex +
                '}';
    }
}