package com.dtrade.repository.tradeorder;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {


    @Query("select to from TradeOrder to where to.traderOrderStatus = 'CREATED' " +
            "or to.traderOrderStatus = 'IN_MARKET'  order by to.creationDate desc")
    List<TradeOrder> getLiveTradeOrders();

    @Query("select to from TradeOrder to where to.account.id =  :#{#account.id} and (to.traderOrderStatus = 'CREATED' " +
            "or to.traderOrderStatus = 'IN_MARKET' ) order by to.creationDate desc")
    Page<TradeOrder> getLiveTradeOrdersByAccount(@Param("account") Account account, Pageable pageable);


    @Query("select to from TradeOrder to where to.account.id =  :#{#account.id} " +
            " and to.tradeOrderType = 'BUY' and (to.traderOrderStatus = 'CREATED' " +
            "or to.traderOrderStatus = 'IN_MARKET' ) ")
    List<TradeOrder> getBuyOpenTradesByAccount(@Param("account") Account account);

    @Query("select to from TradeOrder to where to.account.id =  :#{#account.id} " +
            " and to.diamond.id = :#{#diamond.id}" +
            " and to.tradeOrderType = 'BUY' and (to.traderOrderStatus = 'CREATED' " +
            "or to.traderOrderStatus = 'IN_MARKET' ) ")
    List<TradeOrder> getBuyOpenTradesByAccountAndDiamond(@Param("account") Account account, @Param("diamond") Diamond diamond);

    @Query("select to from TradeOrder to where to.account.id =  :#{#account.id} " +
            " and to.diamond.id = :#{#diamond.id}" +
            " and to.tradeOrderType = 'SELL' and (to.traderOrderStatus = 'CREATED' " +
            "or to.traderOrderStatus = 'IN_MARKET' ) ")
    List<TradeOrder> getSellTradesByAccountAndDiamond(@Param("account") Account account, @Param("diamond") Diamond diamond);

    @Query(value = "select to from TradeOrder to where to.account.id = :#{#account.id} and ( to.traderOrderStatus = 'CANCELED' " +
            " or to.traderOrderStatus = 'EXECUTED' or to.traderOrderStatus = 'REJECTED') order by to.creationDate desc")
    Page<TradeOrder> getHistoryTradeOrdersForAccount(@Param("account") Account account, Pageable pageable);

    @Query("select to from TradeOrder to where to.traderOrderStatus = 'EXECUTED' and to.diamond.id = :#{#diamond.id} order by to.executionDate desc ")
    List<TradeOrder> getHistoryTradeOrders(@Param("diamond") Diamond diamond, Pageable pageable);

    /*
    @Query(" select sum(to.price * to.initialAmount) from TradeOrder to where " +
            "to.account.id = :#{#account.id} and to.traderOrderStatus = 'EXECUTED' " +
            " and to.tradeOrderType='SELL' " +
            " and to.executionDate > :startOfTheMonth " +
            " order by to.creationDate desc ")
    long getSellSumForMonthForAccount(Account account, long startOfTheMonth);*/

    /*
    @Query(" select sum(to.price * to.initialAmount) from TradeOrder to where " +
            "to.account.id = :#{#account.id} and to.traderOrderStatus = 'EXECUTED' " +
            " and to.tradeOrderType='SELL' " +
            " and to.executionDate > :startOfTheMonth " +
            " order by to.creationDate desc ")
    long getBuySumForMonthForAccount();*/
}
