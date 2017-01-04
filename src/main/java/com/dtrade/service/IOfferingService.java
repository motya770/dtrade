package com.dtrade.service;


import com.dtrade.exception.TradeException;
import com.dtrade.model.offering.Offering;

import java.math.BigDecimal;

/**
 * Created by kudelin on 1/4/17.
 */
public interface IOfferingService {

    Offering createOffering(Long fromAccountId, Long toAccountId, Long diamondId, BigDecimal price) throws TradeException;

    Offering acceptOffering(Long offeringId, Long accountId) throws TradeException;

    Offering rejectOffering(Long offeringId, Long accountId) throws TradeException;

    Offering cancelOffering(Long offeringId, Long accountId) throws TradeException;
}

