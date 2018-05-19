package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.CoinPaymentRequest;
import org.springframework.data.domain.Page;

public interface ICoinPaymentService {

    void proceed(CoinPaymentRequest coinPaymentRequest);

    CoinPayment create(CoinPaymentRequest coinPaymentRequest);

    CoinPayment findByExternalId(String externalId);

    Page<CoinPayment> getAllByAccount(Account account);

    CoinPayment confirmPayment(CoinPayment coinPayment);


   // void login();

  //  void deposit();

   // void withdraw();

}
