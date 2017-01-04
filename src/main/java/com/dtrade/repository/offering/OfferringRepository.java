package com.dtrade.repository.offering;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.offering.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kudelin on 1/4/17.
 */
@Repository
@Transactional
public interface OfferringRepository extends JpaRepository<Offering, Long> {

}
