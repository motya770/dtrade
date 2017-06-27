package com.dtrade.service.impl;

import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kudelin on 6/27/17.
 */
@Service
public class TradeOrderService  implements ITradeOrderService{

    @Autowired
    private TradeOrderRepository tradeOrderRepository;



}
