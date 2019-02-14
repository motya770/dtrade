package com.dtrade.model.quote;

import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimpleQuote {

    private Diamond diamond;
    private BigDecimal bid;
    private BigDecimal ask;
}
