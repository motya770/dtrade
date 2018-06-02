package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.InWithdrawRequest;
import com.dtrade.model.coinpayment.OutWithdrawRequest;
import org.springframework.data.domain.Page;

public interface ICoinPaymentService {

    void proceedDeposit(DepositRequest depositRequest);

    void proceedWithdraw(CoinPayment coinPayment);

    CoinPayment sendWithdraw(InWithdrawRequest outWithdrawRequest);

    CoinPayment createWithdraw(InWithdrawRequest outWithdrawRequest);

    CoinPayment createDeposit(DepositRequest depositRequest);

    CoinPayment findByExternalId(String externalId);

    Page<CoinPayment> getAllByAccount(Account account);

    CoinPayment confirmPayment(CoinPayment coinPayment);

    CoinPayment confirmWithdraw(CoinPayment coinPayment);

    void checkHmac(String hmac, String body);

    void requestWithdraw(String id);

    void requestDeposit(String id);


   // void login();

  //  void deposit();

   // void withdraw();

}
