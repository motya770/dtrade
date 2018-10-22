package com.dtrade.repository.balanceactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 12/4/16.
 */

@Repository
public interface BalanceActivityRepository extends JpaRepository<BalanceActivity, Long> {


    List<BalanceActivity> findAllBySellOrder(TradeOrder tradeOrder);

    List<BalanceActivity> findAllByBuyOrder(TradeOrder tradeOrder);

    @Query("select ba from BalanceActivity ba where ba.account.id = :#{#account.id} " +
            " order by ba.createDate desc")
    Page<BalanceActivity> getByAccount(@Param("account") Account account, Pageable pageable);
}
