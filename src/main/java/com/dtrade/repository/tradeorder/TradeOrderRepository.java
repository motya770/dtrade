package com.dtrade.repository.tradeorder;

import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kudelin on 6/27/17.
 */
@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {

}
