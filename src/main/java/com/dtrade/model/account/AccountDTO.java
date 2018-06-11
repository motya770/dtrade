package com.dtrade.model.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {

    private Long id;

    private String mail;

    private BigDecimal balance;
}
