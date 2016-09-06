package com.dtrade.repository.diamond;

import com.dtrade.model.diamond.Diamond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Repository
public interface DiamondRepository extends JpaRepository<Diamond, Long> {

    //@Query("select d from Diamond d where diamondStatus = 'AVAILABLE' ")
    @Query("select d from Diamond d ")
    List<Diamond> getAvailable();

    @Query("select d from Diamond d ")
    List<Diamond> getSale();

    @Query("select d from Diamond d where diamondStatus = 'OWNDED' and account.id = :accountId")
    List<Diamond> getOwned(@Param("accountId") Long accountId);
}
