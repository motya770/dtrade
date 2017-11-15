package com.dtrade.service;

import java.math.BigDecimal;

public interface IWalletService {

    void withdrawTokens(String toAddress, BigDecimal amount);

    void depositTokens(String fromAddress, BigDecimal amount);

}
