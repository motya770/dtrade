package com.dtrade.model.stockactivity;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
@Data
@Entity
public class StockActivity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Diamond diamond;

    @ManyToOne
    @NotNull
    private TradeOrder buyOrder;

    @ManyToOne
    @NotNull
    private TradeOrder sellOrder;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Long createDate;

}
