package com.dtrade.model.quote;

import com.dtrade.model.diamond.Diamond;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
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

    @Column(precision = 12, scale = 5)
    private BigDecimal value;

    private Long time;
}
