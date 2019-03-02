package com.dtrade.model.balance;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalancePos extends Balance {

    private BigDecimal sellSum;

    private BigDecimal avgPrice;

    private BigDecimal todayProfit;

    private BigDecimal todayProfitPercent;

    private BigDecimal generalProfit;

    private BigDecimal generalProfitPercent;

}
