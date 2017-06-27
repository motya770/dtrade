package com.dtrade.model.tradeorder;

import com.dtrade.model.diamond.Diamond;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
@Data
@Entity
public class TradeOrder implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Diamond diamond;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long creationDate;

    @NotNull
    private Long executionDate;

    @NotNull
    private TraderOrderStatus traderOrderStatus;

}