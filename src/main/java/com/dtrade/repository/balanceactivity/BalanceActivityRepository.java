package com.dtrade.repository.balanceactivity;

import com.dtrade.model.balanceactivity.BalanceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kudelin on 12/4/16.
 */

@Repository
public interface BalanceActivityRepository extends JpaRepository<BalanceActivity, Long> {

}

