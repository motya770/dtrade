package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IDiamondService {

    void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException;

    Diamond find(Long diamondId);

    BigDecimal calculateScore();

    List<Diamond> getMyDiamondsOwned();

    List<Diamond> getMyDiamondsForSale();

    List<Diamond> getAvailable();

    List<Diamond> getAllAvailable();

    Diamond create(Diamond diamond);

    void preBuyDiamond(Diamond diamond, Long buyerId, Long sellerId, BigDecimal price) throws TradeException;

    Diamond buyDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price)  throws TradeException;

    Diamond sellDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException;

    Diamond openForSale(Diamond diamond, BigDecimal price) throws TradeException;
}
