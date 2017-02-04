package com.dtrade.service;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;


import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
public interface IDiamondService {

    void update(Diamond diamond);

    void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException;

    Diamond find(Long diamondId);

    BigDecimal calculateScore(Diamond diamond);

    List<Diamond> getMyDiamondsOwned();

    List<Diamond> getMyDiamondsForSale();

    List<Diamond> getAvailable();

    List<Diamond> getAllAvailable();

    Page<Diamond> getAllDiamonds();

    Diamond create(Diamond diamond);

    Diamond preBuyDiamond(Diamond diamond, Long buyerId, Long sellerId, BigDecimal price) throws TradeException;

    Diamond buyDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price)  throws TradeException;

    Diamond sellDiamond(Diamond diamond, Account buyer, Account seller, BigDecimal price) throws TradeException;

    Diamond openForSale(Diamond diamond, BigDecimal price) throws TradeException;
}
