package com.dtrade.service.impl;

import com.dtrade.service.IWalletService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
public class WalletService implements IWalletService {

    @PostConstruct
    private void init()throws Exception{
        /*
        Web3j web3 = Web3j.build(new HttpService());
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("clientVersion " + clientVersion);
        */
    }

    @Override
    public void depositTokens(String fromAddress, BigDecimal amount) {

    }

    @Override
    public void withdrawTokens(String toAddress, BigDecimal amount) {

    }
}
