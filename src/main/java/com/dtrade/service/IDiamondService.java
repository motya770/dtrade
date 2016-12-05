package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IDiamondService {


    List<Diamond> getMyDiamondsOwned();

    List<Diamond> getMyDiamondsForSale();

    List<Diamond> getAvailable();

    Diamond create(Diamond diamond);

    Diamond buyDiamond(Diamond diamond)  throws TradeException;

    Diamond sellDiamond(Diamond diamond);
}
