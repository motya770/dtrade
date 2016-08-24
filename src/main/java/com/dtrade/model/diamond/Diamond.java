package com.dtrade.model.diamond;

import com.dtrade.model.account.Account;

import javax.persistence.*;

/**
 * Created by kudelin on 8/24/16.
 */
@Entity
public class Diamond {

    @Id
    @GeneratedValue
    private Long id;

    private DiamondStatus diamondStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiamondStatus getDiamondStatus() {
        return diamondStatus;
    }

    public void setDiamondStatus(DiamondStatus diamondStatus) {
        this.diamondStatus = diamondStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
