package com.dtrade.model.activity;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by kudelin on 8/24/16.
 */
@Data
@Entity
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account seller;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account buyer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Diamond diamond;

    private BigDecimal sum;

    private Long date;

}
