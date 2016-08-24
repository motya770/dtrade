package com.dtrade.model.account;

import com.dtrade.model.diamond.Diamond;

import javax.persistence.*;
import java.util.List;

/**
 * C
 * zreated by kudelin on 8/24/16.
 */
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Diamond> ownedDiamonds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Diamond> getOwnedDiamonds() {
        return ownedDiamonds;
    }

    public void setOwnedDiamonds(List<Diamond> ownedDiamonds) {
        this.ownedDiamonds = ownedDiamonds;
    }
}
