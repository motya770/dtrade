package com.dtrade.controller;

import com.dtrade.model.balance.BalancePos;
import com.dtrade.model.balance.DepositWithdraw;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/balance", method = RequestMethod.POST)
@RestController
public class BalanceController {

    @Autowired
    private IBalanceService balanceService;

    @Autowired
    private IAccountService accountService;

    @PostMapping(value = "/get-deposits-withdrawals")
    public DepositWithdraw getDepositWithdraw(){
        return balanceService.getDepositWithdraw();
    }

    @PostMapping(value = "/get-balances")
    public List<BalancePos> getBalancesByAccount(){
        return balanceService.getBalancesByAccount(accountService.getStrictlyLoggedAccount());
    }
}
