package com.dtrade.model.quote;

import com.dtrade.model.diamond.Diamond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;


@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote implements Serializable {

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

    @Column(precision = 19, scale = 8)
    private BigDecimal ask;

    @Column(precision = 19, scale = 8)
    private BigDecimal bid;

    @Column(precision = 19, scale = 8)
    private BigDecimal avg;

    @Column(precision = 19, scale = 8)
    private BigDecimal price;

    //TODO think about index
    //@Index()
    private Long time;

    @NotNull
    @Enumerated(EnumType.STRING)
    private QuoteType quoteType;
}
