package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IDiamondService {
    List<Diamond> getOwned();
}
