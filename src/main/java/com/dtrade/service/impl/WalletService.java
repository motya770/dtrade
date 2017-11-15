package com.dtrade.service.impl;

import com.dtrade.service.IWalletService;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
public class WalletService implements IWalletService {

    @PostConstruct
    private void init()throws Exception{
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("clientVersion " + clientVersion);
    }

    @Override
    public void depositTokens(String fromAddress, BigDecimal amount) {

    }

    @Override
    public void withdrawTokens(String toAddress, BigDecimal amount) {

    }
}
