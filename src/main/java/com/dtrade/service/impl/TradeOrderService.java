package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.model.tradeorder.TraderOrderStatus;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.IStockService;
import com.dtrade.service.ITradeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
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

    @Override
    public List<TradeOrder> getLiveTradeOrders() {
        return tradeOrderRepository.getLiveTradeOrders();
    }

    @Autowired
    private IBookOrderService bookOrderService;

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

        BigDecimal cash = realAmount.multiply(buyPrice);

        if(buyAccount.getBalance().compareTo(cash) < 0){
            return;
        }



        /*

        final TradeOrder tradeOrderCopy = tradeOrderRepository.findOne(tradeOrder.getId());
        accountService.checkCurrentAccount(tradeOrder.getAccount());

        Account account = tradeOrder.getAccount();

        if(account.getId().equals(tradeParticipant.getId())){
            throw new TradeException("Can't make trade in the same account");
        }

        List<Stock> stocks = account.getStocks();

        //TODO thing about optimization and sql request
        TradeOrderType type = tradeOrder.getTradeOrderType();

        Stock tradeOrderStock = stockService.getSpecificStock(account, tradeOrder.getDiamond());
        Stock tradeParticipantStock = stockService.getSpecificStock(tradeParticipant, tradeOrder.getDiamond());


        if(type.equals(TradeOrderType.BUY)){

            if(tradeOrderStock == null) {

                tradeOrderStock = new Stock();

            }


        }else if (type.equals(TradeOrderType.SELL)){


        }

        return null;
        */
    }
}
