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
                entityManager.createQuery("SELECT o FROM Offerring o", Offering.class);
        return query.getResultList();
    }
}
