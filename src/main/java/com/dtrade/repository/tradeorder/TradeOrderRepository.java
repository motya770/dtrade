package com.dtrade.repository.tradeorder;

import com.dtrade.model.account.Account;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.domain.PageRequest;
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
            "or to.traderOrderStatus = 'IN_MARKET' order by to.creationDate desc")
    List<TradeOrder> getLiveTradeOrders();

    @Query("select to from TradeOrder to where to.account.id =  :#{#account.id} and (to.traderOrderStatus = 'CREATED' " +
            "or to.traderOrderStatus = 'IN_MARKET' ) order by to.creationDate desc")
    List<TradeOrder> getLiveTradeOrdersByAccount(@Param("account") Account account, Pageable pageable);

    @Query("select to from TradeOrder to where to.account.id = :#{#account.id} and to.traderOrderStatus <> 'CREATED' " +
            "and to.traderOrderStatus <> 'IN_MARKET' order by to.creationDate desc")
    List<TradeOrder> getHistoryTradeOrdersForAccount(@Param("account") Account account);

    @Query("select to from TradeOrder to where to.traderOrderStatus = 'EXECUTED' order by to.executionDate desc ")
    List<TradeOrder> getHistoryTradeOrders(Pageable pageable);
}
