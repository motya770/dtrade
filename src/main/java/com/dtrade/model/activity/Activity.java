package com.dtrade.model.activity;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by kudelin on 8/24/16.
 */
@Entity
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Diamond diamond;

    private BigDecimal sum;

    private Long date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSeller() {
        return seller;
    }

    public void setSeller(Account seller) {
        this.seller = seller;
    }

    public Account getBuyer() {
        return buyer;
    }

    public void setBuyer(Account buyer) {
        this.buyer = buyer;
    }

    public Diamond getDiamond() {
        return diamond;
    }

    public void setDiamond(Diamond diamond) {
        this.diamond = diamond;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
