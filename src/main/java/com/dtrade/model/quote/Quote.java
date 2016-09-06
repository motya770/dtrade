package com.dtrade.model.quote;

import com.dtrade.model.diamond.Diamond;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Entity(name = "quote")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quote  {

    public static final String F_TIME = "time";
    public static final String F_VALUE = "value";

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Diamond diamond;

    @Column(precision = 12, scale = 5)
    private BigDecimal value;

    private Long time;
}
