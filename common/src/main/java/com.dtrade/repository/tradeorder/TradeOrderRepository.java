package com.dtrade.repository.tradeorder;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by kudelin on 6/27/17.
 */
@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {

//    @Query(value = "select * from trade_order where trader_order_status = 'EXECUTED' and diamond_id = ?1 and execution_date > ?2 order by execution_date desc limit 23",
//            nativeQuery = true)

    @Query(nativeQuery = true,
            value = "select * from trade_order as tradeOrder where tradeOrder.trader_order_status_index = 'LIVE' and tradeOrder.trade_order_direction = ?1 and tradeOrder.diamond_id =  ?2 order by tradeOrder.creation_date desc limit 100")
    //@Query("select to from TradeOrder to where to.traderOrderStatusIndex = 'LIVE' and to.tradeOrderDirection = :#{#direction} and diamond.id =  :#{#diamond.id} order by to.creationDate desc")
    List<TradeOrder> getLiveTradeOrdersByDiamondDirection(@Param("diamond") Diamond diamond, @Param("direction") TradeOrderDirection direction);

    @Query("select to from TradeOrder to where to.account.id =  :#{#account.id} and (to.traderOrderStatusIndex =  'LIVE') " +
            " order by to.creationDate desc")
    Page<TradeOrder> getLiveTradeOrdersByAccount(@Param("account") Account account, Pageable pageable);

    @Query(value = "select to from TradeOrder to where to.account.id = :#{#account.id} and ( to.traderOrderStatusIndex = 'HISTORY') " +
            " and to.executionDate >= :#{#startTime} and to.executionDate <= :#{#endTime} " +
            " order by to.creationDate desc ")
    Page<TradeOrder> getHistoryTradeOrdersForAccount(@Param("account") Account account, Long startTime, Long endTime, Pageable pageable);

    @Query(value = "select * from trade_order where trader_order_status = 'EXECUTED' and diamond_id = ?1 and execution_date > ?2 order by execution_date desc limit 23",
            nativeQuery = true)
    //@Query("select to from TradeOrder to where to.traderOrderStatus = 'EXECUTED' and to.diamond.id = :#{#diamond.id} order by to.executionDate desc ")
    List<TradeOrder> getHistoryTradeOrders(Long diamondId, Long time);

    @Query(value = "select sum(to.executionSum) / sum(to.initialAmount) from TradeOrder to where to.account.id = :#{#account.id} and to.traderOrderStatus = 'EXECUTED' " +
            " and to.diamond.id = :#{#diamond.id} and to.tradeOrderDirection = 'BUY' ")
    BigDecimal getAvaragePositionPrice(@Param("account") Account account, @Param("diamond") Diamond diamond);

    @Query(value = "select sum(to.executionSum)  from TradeOrder to where to.account.id = :#{#account.id} and to.traderOrderStatus = 'EXECUTED' " +
            " and to.diamond.id = :#{#diamond.id} and to.tradeOrderDirection = :#{#tradeOrderDirection} and to.executionDate >= :#{#executionDate} ")
    Optional<BigDecimal> getTotalPositionExpences(@Param("account") Account account, @Param("diamond") Diamond diamond, TradeOrderDirection tradeOrderDirection, Long executionDate);

    @Query(value = "select sum(to.initialAmount) from TradeOrder to where to.account.id = :#{#account.id} and to.traderOrderStatus = 'EXECUTED' " +
            " and to.diamond.id = :#{#diamond.id} and to.tradeOrderDirection = :#{#tradeOrderDirection} and to.executionDate < :#{#executionDate} ")
    Optional<BigDecimal> getTotalPositionInitialAmount(@Param("account") Account account, @Param("diamond") Diamond diamond,
                                                       TradeOrderDirection tradeOrderDirection, Long executionDate);
}

  /*
    @Query("select sum((to.amount * to.price))  from TradeOrder to where to.account.id =  :#{#account.id} " +
            " and to.tradeOrderDirection = 'BUY' and (to.traderOrderStatusIndex = 'LIVE' )")
    BigDecimal getBuyOpenTradesByAccount(@Param("account") Account account);*/

    /*
    @Query("select to from TradeOrder to where to.account.id =  :#{#account.id} " +
            " and to.diamond.id = :#{#diamond.id}" +
            " and to.tradeOrderDirection = 'BUY' and (to.traderOrderStatus = 'CREATED' " +
            "or to.traderOrderStatus = 'IN_MARKET' ) ")
    List<TradeOrder> getBuyOpenTradesByAccountAndDiamond(@Param("account") Account account, @Param("diamond") Diamond diamond);
    */

    /*
    @Query("select sum(to.amount) from TradeOrder to where to.account.id =  :#{#account.id} " +
            " and to.diamond.id = :#{#diamond.id}" +
            " and to.tradeOrderDirection = 'SELL' and (to.traderOrderStatusIndex = 'LIVE' ) ")
    BigDecimal getSellTradesByAccountAndDiamond(@Param("account") Account account, @Param("diamond") Diamond diamond);*/

        /*
    @Query(" select sum(to.price * to.initialAmount) from TradeOrder to where " +
            "to.account.id = :#{#account.id} and to.traderOrderStatus = 'EXECUTED' " +
            " and to.tradeOrderDirection='SELL' " +
            " and to.executionDate > :startOfTheMonth " +
            " order by to.creationDate desc ")
    long getSellSumForMonthForAccount(Account account, long startOfTheMonth);*/

    /*
    @Query(" select sum(to.price * to.initialAmount) from TradeOrder to where " +
            "to.account.id = :#{#account.id} and to.traderOrderStatus = 'EXECUTED' " +
            " and to.tradeOrderDirection='SELL' " +
            " and to.executionDate > :startOfTheMonth " +
            " order by to.creationDate desc ")
    long getBuySumForMonthForAccount();*/