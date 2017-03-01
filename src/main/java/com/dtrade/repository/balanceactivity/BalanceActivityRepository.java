package com.dtrade.repository.balanceactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.balanceactivity.BalanceActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 12/4/16.
 */

@Repository
public interface BalanceActivityRepository extends JpaRepository<BalanceActivity, Long> {

    List<BalanceActivity> findByAccount(Account account);

}

