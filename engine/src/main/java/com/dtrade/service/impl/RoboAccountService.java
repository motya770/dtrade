package com.dtrade.service.impl;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IRoboAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class RoboAccountService implements IRoboAccountService {

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IAccountService accountService;

    @EventListener(ContextRefreshedEvent.class)
    public void init(){
        Runnable runnable = ()-> {
            createRoboAccounts();
        };

        Executors.newSingleThreadScheduledExecutor().execute(runnable);
        

    }

    //TODO hardcoded - change
    @Override
    public Account createRoboAccount(String email){
        Account account = accountService.createRealAccount(email, "qwerty1345", null, null);
        account.setRoboAccount(true);
        return accountService.save(account);
    }

    @Override
    public void createRoboAccounts() {
        List<Diamond> diamonds = diamondService.getAllAvailable("");
        diamonds.forEach(diamond -> {
            for(int i = 0; i < accountService.MAX_ROBO_ACCOUNT_COUNT; i ++) {
                String mail = accountService.getRoboAccountMail(diamond, i);
                Account account  = accountService.findByMail(mail);
                if(account==null){
                    try {
                        account = createRoboAccount(mail);
                        log.info("robo account is created for {}, account:  {} ", diamond, account);
                    }catch (Exception e){
                        log.error("{}", e);
                    }
                }else {
                    break;
                }
            }
        });
    }
}
