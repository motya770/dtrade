package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.stock.StockDTO;
import com.dtrade.model.tradeorder.TradeOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
public interface IStockService {

    List<StockDTO> getStocksByAccount();

    List<Stock> findAll();

    void makeIPO(Long diamondId);

    boolean fieldsNotEmpty(Stock stock);

    Stock getSpecificStock(Account account, Diamond diamond);

    Stock updateStockInTrade(TradeOrder tradeOrder, Account account, Diamond diamond, BigDecimal stockAmount);

    Stock updateRoboStockAmount(Diamond diamond, Account account);

    void save(Stock stock);
}
