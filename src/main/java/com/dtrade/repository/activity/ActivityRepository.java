package com.dtrade.repository.activity;

import com.dtrade.model.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kudelin on 8/24/16.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
