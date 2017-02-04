package com.dtrade.model.diamond;

import com.dtrade.model.account.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kudelin on 8/24/16.
 */
@Data
@Entity
public class Diamond implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @NotNull
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiamondType diamondType;

    private BigDecimal carats;

    private BigDecimal clarity;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiamondStatus diamondStatus;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private BigDecimal score;

    @Override
    public String toString(){
        return "owner: {id:" + id +  "}";
    }
}
