package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
public interface ITradeOrderService {

    TradeOrder afterTradeOrderCreation(TradeOrder order, Account account);

    TradeOrderDTO convert(TradeOrder to);

    List<TradeOrder> rereadTradeOrders(Long[] tradeOrders);

    List<TradeOrderDTO> getTradeOrderDTO(List<TradeOrder> tradeOrders);

    Page<TradeOrder> findAll(Integer pageNumber);

    void calculateTradeOrders();

    void validateFields(TradeOrder tradeOrder);

    Page<TradeOrder> getHistoryTradeOrdersByAccount(Integer pageNumber);

    List<TradeOrder> getHistoryTradeOrders(Long diamondId);

    List<TradeOrder> getHistoryTradeOrdersCashed(Long diamondId);

    List<TradeOrder> getLiveTradeOrders();

    Page<TradeOrder> getLiveTradeOrdersByAccount(Integer pageNumber);

    TradeOrder createTradeOrder(TradeOrder tradeOrder);

    TradeOrder rejectTradeOrder(TradeOrder sellOrder);

    TradeOrder cancelTradeOrder(TradeOrder tradeOrder);

    //buy order // sell order
    Pair<Boolean, Boolean>  executeTradeOrders(Pair<TradeOrder, TradeOrder> pair);

    boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair);
}
