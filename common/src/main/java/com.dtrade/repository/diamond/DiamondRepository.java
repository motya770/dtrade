package com.dtrade.repository.diamond;

import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Repository
@Transactional
public interface DiamondRepository extends JpaRepository<Diamond, Long> {

    @Query("select d from Diamond d where diamondStatus = 'ENLISTED' and d.name like %:name% order by d.name")
    List<Diamond> getAllAvailableByName(@Param("name") String name);

    @Query("select d from Diamond d where diamondStatus = 'ENLISTED' or diamondStatus = 'ROBO_HIDDEN'")
    List<Diamond> getEnlistedAndRoboHidded();

    Diamond findByName(String name);

    Diamond findByCurrencyAndDiamondStatus(Currency currency, DiamondStatus diamondStatus);

    @Query("select d from Diamond d where score >= :lowerBound and score < :upperBound ")
    List<Diamond> getDiamondsByScoreBounds(@Param("lowerBound") int lowerBound, @Param("upperBound") int upperBound);
}
