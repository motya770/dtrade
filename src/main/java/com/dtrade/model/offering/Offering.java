package com.dtrade.model.offering;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kudelin on 1/4/17.
 */

@Data
@Entity
public class Offering implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account seller;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account buyer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Diamond diamond;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OfferingType offeringType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OfferingStatus offeringStatus;

    @NotNull
    private Long createDate;

}
