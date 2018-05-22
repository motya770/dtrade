package com.dtrade.model.quote;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QuoteDTO {

    private Long time;

    private BigDecimal avg;
}
