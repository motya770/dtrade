package com.dtrade.model.account;

import com.dtrade.model.balance.BalanceDTO;
import lombok.Data;

@Data
public class AccountDTO {

    private Long id;

    private String mail;

    private BalanceDTO balance;
}
