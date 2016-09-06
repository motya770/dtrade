package com.dtrade.repository.quote;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 9/6/16.
 */
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query("select q from Quote q where q.diamond.id = :diamond and time >= :start_time and time <= :end_time ")
    List<Quote> getRangeQuotes(@Param("diamond") Long diamondId, @Param("start_time") Long start, @Param("end_time") Long end);
}
