package com.dtrade.repository.offering;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.offering.Offering;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by kudelin on 1/7/17.
 */

//@RepositoryDefinition()
public class OfferringRepositoryImpl implements OfferringRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OfferringRepository offerringRepository;

    @Override
    public List<Offering> getPreviousLiveOfferingsForDiamond(Diamond diamond, Offering offering) {
       TypedQuery<Offering> query =
                entityManager.createQuery("SELECT c FROM Offering c " +
                        "where c.diamond = :diamond and c.offeringType = :offeringType " , Offering.class);

       query.setParameter("diamond", diamond);
       query.setParameter("offeringType", offering.getOfferingType());
       //query.setParameter("currentTime", System.currentTimeMillis());

       return query.getResultList();
    }
}
