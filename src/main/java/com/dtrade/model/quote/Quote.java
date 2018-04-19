package com.dtrade.model.quote;

import com.dtrade.model.diamond.Diamond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote  {

    public static final String F_TIME = "time";
    public static final String F_VALUE = "value";

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Diamond diamond;

    //@Column(precision = 12, scale = 5)
    //private BigDecimal value;

    @Column(precision = 12, scale = 5)
    private BigDecimal ask;

    @Column(precision = 12, scale = 5)
    private BigDecimal bid;

    @Column(precision = 12, scale = 5)
    private BigDecimal avg;

    @Column(precision = 12, scale = 5)
    private BigDecimal price;

    //TODO think about index
    //@Index()
    private Long time;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuoteType quoteType;
}
