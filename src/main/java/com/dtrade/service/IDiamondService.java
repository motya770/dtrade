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

    Diamond buyDiamond(Diamond diamond, BigDecimal price)  throws TradeException;

    Diamond openForSale(Diamond diamond, BigDecimal price) throws TradeException;
}
