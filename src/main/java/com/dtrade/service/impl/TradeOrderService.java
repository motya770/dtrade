package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.model.tradeorder.TraderOrderStatus;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
@Transactional
@Service
public class TradeOrderService  implements ITradeOrderService{

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private IStockService stockService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Override
    public List<TradeOrder> getLiveTradeOrders() {
        return tradeOrderRepository.getLiveTradeOrders();
    }

    @Autowired
    private IBookOrderService bookOrderService;

    @Autowired
    private IStockActivityService stockActivityService;

    private BigDecimal zeroValue = new BigDecimal("0.00");

    //the reason i use stock object in this case is just a convenience
    @Override
    public TradeOrder createTradeOrder(Stock stock, BigDecimal price) {

        //TODO maybe we should price too.
        //TODO check account balance.
        //TODO freeze money (?)

        if(!stockService.fieldsNotEmpty(stock)){
            throw new TradeException("Can't create stock because stock is empty");
        }

        accountService.checkCurrentAccount(stock.getAccount());

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setAmount(stock.getAmount());
        tradeOrder.setDiamond(stock.getDiamond());
        tradeOrder.setAccount(stock.getAccount());
        tradeOrder.setPrice(price);
        tradeOrder.setTraderOrderStatus(TraderOrderStatus.CREATED);
        tradeOrder.setCreationDate(System.currentTimeMillis());

        tradeOrder = tradeOrderRepository.save(tradeOrder);

        bookOrderService.addNew(tradeOrder);

        return tradeOrder;
    }

    @Override
    public TradeOrder cancelTradeOrder(TradeOrder tradeOrder) {
        if(tradeOrder==null){
            throw new TradeException("TradeOrder is null");
        }

        tradeOrder = tradeOrderRepository.getOne(tradeOrder.getId());
        accountService.checkCurrentAccount(tradeOrder.getAccount());

        if(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED)
                || tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CANCELED))
        {
            throw new TradeException("Can't cancel trader order with status " + tradeOrder.getTraderOrderStatus());
        }


        tradeOrder.setTraderOrderStatus(TraderOrderStatus.CANCELED);

        tradeOrder = tradeOrderRepository.save(tradeOrder);

        bookOrderService.remove(tradeOrder);

        return tradeOrder;
    }

    @Override
    public boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair) {

        if(pair==null){
            return false;
        }

        TradeOrder buyOrder = pair.getFirst();
        TradeOrder sellOrder = pair.getSecond();

        if(buyOrder==null || sellOrder == null){
            return false;
        }

        if(buyOrder.getPrice().compareTo(sellOrder.getPrice()) >= 0){
            return true;
        }

        return false;
    }

    //buy - sell
    @Override
    public void executeTradeOrders(Pair<TradeOrder, TradeOrder> pair) {

        /*
            1) Simple example of market order should be produced
            1a) We assume that on market orders exists
            2) Take sellers(!) stock and transfer to buyer
            3) Transfer money
            4) Write all activities
            5) work only during one minute
         */

        TradeOrder buyOrder = tradeOrderRepository.findOne(pair.getFirst().getId());
        TradeOrder sellOrder = tradeOrderRepository.findOne(pair.getSecond().getId());

        if(!buyOrder.getDiamond().equals(sellOrder.getDiamond())){
            return;
        }

        if(!buyOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)){
            return;
        }

        if(!sellOrder.getTraderOrderStatus().equals(TraderOrderStatus.IN_MARKET)){
            return;
        }

        if(!checkIfCanExecute(pair)){
            return;
        }

        Account buyAccount = buyOrder.getAccount();
        Account sellAccount = sellOrder.getAccount();

        if(buyAccount.equals(sellAccount)){
            return;
        }

        BigDecimal buyPrice = buyOrder.getPrice();
        BigDecimal buyAmount = buyOrder.getAmount();
        BigDecimal sellAmount = sellOrder.getAmount();

        BigDecimal realAmount = buyAmount.min(sellAmount);

        Stock buyStock = stockService.getSpecificStock(buyAccount, buyOrder.getDiamond());
        Stock sellStock = stockService.getSpecificStock(sellAccount, sellOrder.getDiamond());

        //seller don't have enouth stocks
        if(sellStock.getAmount().compareTo(realAmount) < 0){
            throw new TradeException("Not enougth stocks at " + sellStock);
        }

        BigDecimal cash = realAmount.multiply(buyPrice);

        balanceActivityService.createBalanceActivities(buyAccount, sellAccount, cash, buyOrder, sellOrder);

        buyStock.getAmount().add(realAmount);
        sellStock.getAmount().subtract(realAmount);

        stockActivityService.createStockActivity( buyOrder, sellOrder,
                 cash, realAmount);

        if(buyOrder.equals(zeroValue)){
            bookOrderService.remove(buyOrder);
            buyOrder.setTraderOrderStatus(TraderOrderStatus.EXECUTED);
        }else {
            bookOrderService.update(buyOrder);
        }

        if(sellOrder.equals(zeroValue)){
            bookOrderService.remove(sellOrder);
            sellOrder.setTraderOrderStatus(TraderOrderStatus.EXECUTED);
        }else{
            bookOrderService.update(sellOrder);
        }

        stockService.save(buyStock);
        stockService.save(sellStock);

        tradeOrderRepository.save(buyOrder);
        tradeOrderRepository.save(sellOrder);

        accountService.save(buyAccount);
        accountService.save(sellAccount);
    }
}
