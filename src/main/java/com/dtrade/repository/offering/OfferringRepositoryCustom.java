package com.dtrade.repository.offering;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.offering.Offering;

import java.util.List;

/**
 * Created by kudelin on 1/7/17.
 */

public interface OfferringRepositoryCustom {
    List<Offering> getPreviousLiveOfferingsForDiamond(Diamond diamond, Offering offering);
}
