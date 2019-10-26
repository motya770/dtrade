package com.dtrade.model.categorytick;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 3/5/17.
 */
@Data
@Entity
public class CategoryTick {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    @Id
    private Long id;

    @NotNull
    private Integer score;

    @NotNull
    private BigDecimal avarage;

    @NotNull
    private Long time;
}
