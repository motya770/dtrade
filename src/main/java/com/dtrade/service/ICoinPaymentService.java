package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import org.springframework.data.domain.Page;

public interface ICoinPaymentService {

    void proceed();

    CoinPayment create(String login, String externalId);

    CoinPayment findByExternalId(String externalId);

    Page<CoinPayment> getAllByAccount(Account account);

    CoinPayment confirmPayment(String externalId);


   // void login();

  //  void deposit();

   // void withdraw();

}
