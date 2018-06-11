package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
public interface ITradeOrderService {

    BigDecimal getAllOpenedTradesSum(Account account);

    //BigDecimal getOpenedTradesSum(Account account, Diamond diamond);

    BigDecimal getOpenedStocksAmount(Account account, Diamond diamond);

    List<TradeOrder> rereadTradeOrders(Long[] tradeOrders);

    List<TradeOrderDTO> getTradeOrderDTO(List<TradeOrder> tradeOrders);

    List<TradeOrder> findAll();

    void calculateTradeOrders();

    void validateFields(TradeOrder tradeOrder);

    Page<TradeOrder> getHistoryTradeOrdersByAccount(Integer pageNumber);

    List<TradeOrder> getHistoryTradeOrders(Diamond diamond);

    List<TradeOrder> getLiveTradeOrders();

    Page<TradeOrder> getLiveTradeOrdersByAccount(Integer pageNumber);

    TradeOrder createTradeOrder(TradeOrder tradeOrder);

    TradeOrder rejectTradeOrder(TradeOrder sellOrder);

    TradeOrder cancelTradeOrder(TradeOrder tradeOrder);

    //buy order // sell order
    void executeTradeOrders(Pair<TradeOrder, TradeOrder> pair);

    boolean checkIfCanExecute(Pair<TradeOrder, TradeOrder> pair);

    long sellSumForMonthForAccount();

    long buySumForMonthForAccount();

}
