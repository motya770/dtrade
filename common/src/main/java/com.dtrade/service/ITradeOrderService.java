package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderAccountHolder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
public interface ITradeOrderService {

    public final static BigDecimal ZERO_VALUE = BigDecimal.ZERO;
    public final static BigDecimal MINUS_ONE_VALUE = new BigDecimal("-1.00");

    TradeOrder afterTradeOrderCreation(TradeOrder order, Account account);

    TradeOrderDTO convert(TradeOrder to);

    List<TradeOrder> rereadTradeOrders(Long[] tradeOrders);

    List<TradeOrderDTO> getTradeOrderDTO(List<TradeOrder> tradeOrders);

    Page<TradeOrder> findAll(Integer pageNumber);

    void validateFields(TradeOrder tradeOrder);

    Page<TradeOrder> getHistoryTradeOrdersByAccount(Integer pageNumber);

    List<TradeOrder> getHistoryTradeOrders(Long diamondId);

    List<TradeOrder> getHistoryTradeOrdersCashed(Long diamondId);

    List<TradeOrder> getLiveTradeOrders();

    Page<TradeOrder> getLiveTradeOrdersByAccount(Integer pageNumber);

    TradeOrderAccountHolder createTradeOrder(TradeOrder tradeOrder);

    TradeOrder cancelTradeOrder(TradeOrder tradeOrder);

    BigDecimal definePrice(TradeOrder sellOrder, TradeOrder buyOrder);
}
