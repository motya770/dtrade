package com.dtrade.model.stock;

import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockDTO {

    private Long id;

    private Diamond diamond;

    private BigDecimal amount;
}
