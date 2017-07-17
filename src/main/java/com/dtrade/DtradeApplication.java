package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.model.account.company.Company;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.offering.OfferringRepository;
import com.dtrade.service.IAccountService;
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

import java.math.BigDecimal;

//import java.io.File;
//import java.nio.file.Files;

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


//        Company company = (Company) userDetailsService.loadUserByUsername("motya770@gmail.com");
//        setAccount(company);
//
//        Account buyer = (Account) userDetailsService.loadUserByUsername("motya7701@gmail.com");
//
//        Diamond diamond =  diamondService.getAllAvailable().get(0);
//        stockService.makeIPO(company, diamond);
//
//
//        TradeOrder tradeOrder = new TradeOrder();
//        tradeOrder.setDiamond(diamond);
//        tradeOrder.setAccount(buyer);
//        tradeOrder.setAmount(new BigDecimal("100"));
//        tradeOrder.setPrice(new BigDecimal("20.0"));
//
//        tradeOrderService.createTradeOrder(tradeOrder);
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
