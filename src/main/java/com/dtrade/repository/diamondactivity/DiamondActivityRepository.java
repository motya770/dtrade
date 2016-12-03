package com.dtrade.repository.diamondactivity;

import com.dtrade.model.diamondactivity.DiamondActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kudelin on 8/24/16.
 */
@Repository
public interface DiamondActivityRepository extends JpaRepository<DiamondActivity, Long> {
}
