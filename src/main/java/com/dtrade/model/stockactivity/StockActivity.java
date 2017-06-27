package com.dtrade.model.stockactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
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

    @NotNull
    private Account buyer;

    @NotNull
    private Account seller;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private Long createDate;

}
