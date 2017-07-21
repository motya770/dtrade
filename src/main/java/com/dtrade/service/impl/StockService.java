package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.repository.stock.StockRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by kudelin on 6/27/17.
 */
@Service
@Transactional
public class StockService  implements IStockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IAccountService accountService;

    //TODO refactor - add StockActivity
    @Override
    public void makeIPO(Long diamondId){

        Diamond diamond = diamondService.find(diamondId);

        diamondService.checkDiamondOwnship(accountService.getStrictlyLoggedAccount(), diamond);

        if(diamond.getTotalStockAmount()==null){
            throw new TradeException("Can't produce IPO for diamond " + diamond + " where totalStockAmount is not defined");
        }

       // Hibernate.initialize(diamond.getAccount());

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
        Stock stock = stockRepository.findByAccountAndDiamond(account, diamond);
        if(stock==null){
            stock = new Stock();
            stock.setAmount(new BigDecimal("0.0"));
            stock.setDiamond(diamond);
            stock.setAccount(account);
        }
        return stock;
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
