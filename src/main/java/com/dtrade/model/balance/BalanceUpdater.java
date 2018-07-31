package com.dtrade.model.balance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceUpdater {

    private Balance balance;

    private BigDecimal amount;

    private BigDecimal open;
}
