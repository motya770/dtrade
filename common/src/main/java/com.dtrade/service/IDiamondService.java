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


    void validateDiamondCanTrade(Diamond diamond);

    Diamond defineRobotBorders(Diamond diamond, BigDecimal bid, BigDecimal ask);

    Diamond unsecuredUpdate(Diamond diamond);

    Diamond update(Diamond diamond);

    void checkDiamondOwnship(Account account, Diamond diamond) throws TradeException;

    Diamond find(Long diamondId);

    List<Diamond> getEnlistedOrRoboHidden();

    List<Diamond> getAllAvailable(String name);

    List<DiamondDTO> getAllAvailableDTO(String name);

    Page<Diamond> getAllDiamonds();

    List<Diamond> getDiamondsByScoreBounds(int lowerBound, int upperBound);

    Diamond create(Diamond diamond);
}
