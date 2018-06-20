package com.dtrade.model.quote.depth;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepthQuote {

    private BigDecimal amount;

    private BigDecimal price;

    public DepthQuote(){
        this.amount = new BigDecimal("0.0");
        this.price = new BigDecimal("0.0");
    }
}
