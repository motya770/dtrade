package com.dtrade.service;

import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.coinpayment.DepositRequest;
import com.dtrade.model.coinpayment.InWithdrawRequest;
import org.springframework.data.domain.Page;

public interface ICoinPaymentService {

    Page<CoinPayment> findAll(Integer pageNumber);

    void proceedDeposit(DepositRequest depositRequest);

    void proceedWithdraw(InWithdrawRequest inWithdrawRequest);

    CoinPayment sendWithdraw(InWithdrawRequest outWithdrawRequest);

    CoinPayment createWithdraw(InWithdrawRequest outWithdrawRequest);

    CoinPayment createDeposit(DepositRequest depositRequest);

    Page<CoinPayment> getAllByAccount(Account account);

    CoinPayment confirmPayment(CoinPayment coinPayment);

    CoinPayment confirmWithdraw(CoinPayment coinPayment);

    void checkHmac(String hmac, String body);

    /*
    void requestWithdraw(String id);

    void requestDeposit(String id);
*/

   // void login();

  //  void deposit();

   // void withdraw();

}
