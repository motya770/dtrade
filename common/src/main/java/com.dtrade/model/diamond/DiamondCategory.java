package com.dtrade.model.diamond;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * Created by kudelin on 3/4/17.
 */

@Embeddable
@Data
public class DiamondCategory {

    private Integer from;
    private Integer to;
}
