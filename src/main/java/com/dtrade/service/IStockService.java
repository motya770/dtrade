package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.account.company.Company;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;

/**
 * Created by kudelin on 6/27/17.
 */
public interface IStockService {

    void makeIPO(Company owner, Diamond diamond);

    boolean fieldsNotEmpty(Stock stock);

    Stock getSpecificStock(Account account, Diamond diamond);

    void save(Stock stock);
}
