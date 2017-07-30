package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.repository.offering.OfferringRepository;
import com.dtrade.service.IStockService;
import com.dtrade.service.ITradeOrderService;
import com.dtrade.service.impl.DiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


@SpringBootApplication
public class DtradeApplication  implements CommandLineRunner {


    @Autowired
    private OfferringRepository offerringRepository;


    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private DiamondService diamondService;


    @Autowired
    private ITradeOrderService tradeOrderService;


    @Autowired
    private IStockService stockService;

    @Override
    public void run(String... strings) throws Exception {
        //offerringRepository.findOne(1L);
        ///offerringRepository.getPreviousLiveOfferingsForDiamond(null, null);

        /*
        Account a1 =  (Account) userDetailsService.loadUserByUsername("motya770@gmail.com");
        setAccount(a1);

        Account a2 = (Account) userDetailsService.loadUserByUsername("motya7701@gmail.com");

        Diamond diamond =  diamondService.find(2L);

        //stockService.makeIPO(company, diamond);

        for(int i = 0; i< 1_000; i++) {
            TradeOrder
                    tradeOrder = new TradeOrder();
            tradeOrder.setDiamond(diamond);
            tradeOrder.setAccount(a1);
            tradeOrder.setAmount(new BigDecimal("100"));
            tradeOrder.setPrice(new BigDecimal("20.0"));
            tradeOrder.setTradeOrderType(TradeOrderType.BUY);

            tradeOrderService.createTradeOrder(tradeOrder);
        }

        setAccount(a2);

        for(int i = 0; i< 1_000; i++) {
            TradeOrder tradeOrder = new TradeOrder();
            tradeOrder.setDiamond(diamond);
            tradeOrder.setAccount(a2);
            tradeOrder.setAmount(new BigDecimal("100"));
            tradeOrder.setPrice(new BigDecimal("20.0"));
            tradeOrder.setTradeOrderType(TradeOrderType.SELL);

            tradeOrderService.createTradeOrder(tradeOrder);
        }*/
    }


    private SecurityContext setAccount(Account account){
        UserDetails principal = account;
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    public static void main(String[] args) {

        //File file = new File();
        //Files.readAllLines(file.toPath());

        SpringApplication.run(DtradeApplication.class, args);
    }
}
