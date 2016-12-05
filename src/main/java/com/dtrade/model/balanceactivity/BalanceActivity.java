package com.dtrade.model.balanceactivity;

import com.dtrade.model.account.Account;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by kudelin on 12/3/16.
 */

@Entity
@Data
public class BalanceActivity {


    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Enumerated(EnumType.STRING)
    private BalanceActivityType balanceActivityType;

}
