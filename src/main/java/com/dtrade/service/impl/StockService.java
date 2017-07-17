package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.account.company.Company;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.repository.stock.StockRepository;
import com.dtrade.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kudelin on 6/27/17.
 */
@Service
public class StockService  implements IStockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public void makeIPO(Company owner, Diamond diamond){

        if(diamond.getTotalStockAmount()==null){
            throw new TradeException("Can't produce IPO for diamond " + diamond + " where totalStockAmount is not defined");
        }

        Stock stock = new Stock();
        stock.setAmount(diamond.getTotalStockAmount());
        stock.setAccount(diamond.getAccount());
        stock.setDiamond(diamond);

        stockRepository.save(stock);
    }

    @Override
    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public Stock getSpecificStock(Account account, Diamond diamond) {
        return stockRepository.findByAccountAndDiamond(account, diamond);
    }

    @Override
    public boolean fieldsNotEmpty(Stock stock) {

        if(stock.getDiamond()==null){
            return false;
        }

        if(stock.getAccount()==null){
            return false;
        }

        if(stock.getAmount() == null){
            return false;
        }
        return true;
    }
}
