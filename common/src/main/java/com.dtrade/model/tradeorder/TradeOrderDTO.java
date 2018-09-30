package com.dtrade.model.tradeorder;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeOrderDTO {

    private Long id;

    private BigDecimal amount;

    private BigDecimal initialAmount;

    private BigDecimal price;

    private Long executionDate;

    private Long creationDate;

    private TradeOrderDirection tradeOrderDirection;

    private Long diamondId;
}
