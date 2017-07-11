package com.dtrade.service.impl;

import com.dtrade.repository.stockactivity.StockActivityRepository;
import com.dtrade.service.IStockActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kudelin on 6/27/17.
 */
@Service
public class StockActivityService implements IStockActivityService {


    @Autowired
    private StockActivityRepository stockActivityRepository;

    @Override
    public void createStockActivity() {

    }
}
