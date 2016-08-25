package com.dtrade.model.diamond;

import com.dtrade.model.account.Account;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by kudelin on 8/24/16.
 */
@Data
@Entity
public class Diamond {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private BigDecimal price;

    private DiamondType diamondType;

    private BigDecimal carats;

    private BigDecimal clarity;

    //private List<>

    //Diamond Name	Price	Type	Carats	Clarity	Pic

    private DiamondStatus diamondStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

}
