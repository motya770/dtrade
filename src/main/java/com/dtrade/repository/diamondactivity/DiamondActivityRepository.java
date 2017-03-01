package com.dtrade.repository.diamondactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamondactivity.DiamondActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Repository
public interface DiamondActivityRepository extends JpaRepository<DiamondActivity, Long> {

    @Query("select da from DiamondActivity da where da.buyer = :account or da.seller = :account ")
    List<DiamondActivity> getAccountDiamondActivities(@Param("account") Account account);
}
