package com.dtrade.repository.stockactivity;

import com.dtrade.model.stockactivity.StockActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kudelin on 6/27/17.
 */
@Repository
public interface StockActivityRepository extends JpaRepository<StockActivity, Long> {
}
