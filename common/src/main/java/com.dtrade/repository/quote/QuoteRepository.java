package com.dtrade.repository.quote;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.quote.QuoteType;
import org.springframework.data.domain.Pageable;
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

    //TODO think about subquery!
    @Query("select q from Quote q where q.diamond.id = :diamond " +
            " and q.time > :start_time " +
            " and q.time <= :end_time " +
            " and q.quoteType=:quote_type order by q.time desc ")
    List<Quote> getRangeQuotes(@Param("diamond") Long diamondId,
                               @Param("start_time") Long start,
                               @Param("end_time") Long end, @Param("quote_type")
                                       QuoteType quoteType, Pageable pageable);

    Quote findFirstByDiamondOrderByTimeDesc(Diamond diamond);

    Quote findFirstByDiamondAndTimeIsLessThanEqualOrderByTimeDesc(Diamond diamond, Long time);
}
