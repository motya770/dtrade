package com.dtrade.repository.offering;

import com.dtrade.model.offering.Offering;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kudelin on 1/4/17.
 */
//@Transactional
public interface OfferringRepository extends JpaRepository<Offering, Long>,
        //QueryDslPredicateExecutor<Offering>,
        OfferringRepositoryCustom {


}
