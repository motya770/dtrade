package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.WithdrawRequest;
import org.springframework.data.domain.Page;

public interface ICoinPaymentService {

    void proceedDeposit(DepositRequest depositRequest);

    void proceedWithdraw(WithdrawRequest withdrawRequest);

    CoinPayment create(DepositRequest depositRequest);

    CoinPayment findByExternalId(String externalId);

    Page<CoinPayment> getAllByAccount(Account account);

    CoinPayment confirmPayment(CoinPayment coinPayment);

    CoinPayment createWithdraw();


   // void login();

  //  void deposit();

   // void withdraw();

}
