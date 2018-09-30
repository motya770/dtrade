package com.dtrade.model.categorytick;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by kudelin on 3/5/17.
 */
@Data
@Entity
public class CategoryTick {


    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    private Integer score;

    @NotNull
    private BigDecimal avarage;

    @NotNull
    private Long time;
}
