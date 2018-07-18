package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.stock.StockDTO;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.stock.StockRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IStockService;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Override
    public List<Stock> findAll(){
        return stockRepository.findAll();
    }

    @Override
    public List<StockDTO> getStocksByAccount() {
        Account account = accountService.getStrictlyLoggedAccount();
        List<Stock> stocks = stockRepository.findByAccount(account);
        return stocks.stream().map(stock -> {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setId(stock.getId());
            stockDTO.setAmount(stock.getAmount().subtract(stock.getStockInTrade()));
            stockDTO.setDiamond(stock.getDiamond());
            return stockDTO;
        }).collect(Collectors.toList());
    }

    //TODO refactor - add StockActivity
    @Override
    public void makeIPO(Long diamondId){

        Diamond diamond = diamondService.find(diamondId);

        diamondService.checkDiamondOwnship(accountService.getStrictlyLoggedAccount(), diamond);

        if(diamond.getTotalStockAmount()==null){
            throw new TradeException("Can't produce IPO for diamond " + diamond + " where totalStockAmount is not defined.");
        }

        if(diamond.getDiamondStatus().equals(DiamondStatus.ENLISTED)){
            throw new TradeException("Diamond " + diamond + " already enlisted.");
        }

        Stock stock = new Stock();
        stock.setAmount(diamond.getTotalStockAmount());
        stock.setAccount(diamond.getAccount());
        stock.setDiamond(diamond);
        stock.setStockInTrade(BigDecimal.ZERO);

        diamond.setDiamondStatus(DiamondStatus.ENLISTED);

        diamondService.update(diamond);
        stockRepository.save(stock);
    }

    @Override
    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public Stock updateStockInTrade(TradeOrder tradeOrder, Account account, Diamond diamond, BigDecimal stockAmount) {

        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)){
            Stock stock = getSpecificStock(account, diamond);
            if(stock==null){
                throw new TradeException("Can't find specific stock");
            }

            stock.setStockInTrade(stock.getStockInTrade().add(stockAmount));
            stockRepository.save(stock);
            return stock;
        }
        return null;
    }

    @Override
    public Stock getSpecificStock(Account account, Diamond diamond) {
       // System.out.println("AC: " + account);
       // System.out.println("DI: " + diamond);

        Stock stock = stockRepository.getSpecificStock(account, diamond);
        if(stock==null){
            stock = new Stock();
            stock.setAmount(BigDecimal.ZERO);
            stock.setDiamond(diamond);
            stock.setAccount(account);
            stock.setStockInTrade(BigDecimal.ZERO);
            stockRepository.save(stock);
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
