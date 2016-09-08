package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IDiamondService {
    List<Diamond> getOwned();

    Diamond create(Diamond diamond);

    Diamond buy(Diamond diamond);

    Diamond sell(Diamond diamond);
}
