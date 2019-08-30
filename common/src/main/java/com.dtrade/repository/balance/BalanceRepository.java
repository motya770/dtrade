package com.dtrade.repository.balance;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {


//    @Query(value = "select * from trade_order where trader_order_status = 'EXECUTED' and diamond_id = ?1 and execution_date > ?2 order by execution_date desc limit 23",
//            nativeQuery = true)
//        //@Query("select to from TradeOrder to where to.traderOrderStatus = 'EXECUTED' and to.diamond.id = :#{#diamond.id} order by to.executionDate desc ")
//    List<TradeOrder> getHistoryTradeOrders(Long diamondId, Long time);

    //@Query(nativeQuery = true, value = "select * from balance b where b.account_id  = 1 and b.currency = 'BTC'")
    @Query("select b from Balance b where b.account.id  = :#{#account.id} and b.currency = :#{#currency}")
    Balance getBalance(@Param("account") Account account, @Param("currency") Currency currency);

    @Query("select b from Balance b where b.account.id  = :#{#account.id} and b.currency in :currencies")
    List<Balance> getBaseBalancesByAccount(@Param("account") Account account, @Param("currencies") List<Currency> currencies);
}