package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IDiamondService {

    Diamond update(Diamond diamond);

    void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException;

    Diamond find(Long diamondId);

//    List<Diamond> getMyDiamondsOwned();
//
//    List<Diamond> getMyDiamondsForSale();

   // List<Diamond> getAvailable();

    List<Diamond> getAllAvailable(String name);

    Page<Diamond> getAllDiamonds();

    List<Diamond> getDiamondsByScoreBounds(int lowerBound, int upperBound);

    Diamond create(Diamond diamond);

    @Deprecated
    Diamond preBuyDiamond(Diamond diamond, Long buyerId, BigDecimal price) throws TradeException;

    @Deprecated
    Diamond buyDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price)  throws TradeException;

    @Deprecated
    Diamond sellDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException;

    @Deprecated
    Diamond openForSale(Diamond diamond, BigDecimal price) throws TradeException;

    @Deprecated
    Diamond hideFromSale(Diamond diamond);
}
