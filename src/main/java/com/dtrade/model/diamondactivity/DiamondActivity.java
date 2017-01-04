package com.dtrade.model.diamondactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 8/24/16.
 */
@Data
@Entity
public class DiamondActivity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account seller;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account buyer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Diamond diamond;

    private BigDecimal price;

    private Long date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DiamondActivityType diamondActivityType;
}
