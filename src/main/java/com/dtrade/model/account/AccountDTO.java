package com.dtrade.model.account;

import com.dtrade.model.balance.Balance;
import com.dtrade.model.balance.BalanceDTO;
import lombok.Data;

import java.util.List;

@Data
public class AccountDTO {

    private Long id;

    private String mail;

    private List<Balance> balance;
}
