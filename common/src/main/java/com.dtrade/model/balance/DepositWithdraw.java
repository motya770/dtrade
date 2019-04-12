package com.dtrade.model.balance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositWithdraw {

    private BigDecimal deposit;

    private BigDecimal withdraw;
}
