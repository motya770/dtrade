package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderType;
import com.dtrade.model.tradeorder.TraderOrderStatus;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.ITradeOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 7/15/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeOrderServiceTest {

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private IDiamondService diamondService;

    private SecurityContext setAccount(Account account){
        UserDetails principal = account;
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    @Test
    public void test(){

        Account account = accountService.createRealAccount("motya7702@gmail.com", "12345", "+79210827487", "USD");

        accountService.confirmRegistration(account.getGuid());

        SecurityContextHolder.setContext(setAccount(account));

        System.out.println("1233 234 " + tradeOrderService);

    }

    @Test
    public void testFindAll(){
        List<TradeOrder> tradeOrders = tradeOrderService.findAll();
        Assert.assertTrue(tradeOrders.size()>0);
    }

    @Test
    public void testCalculateTradeOrders(){

    }

    @Test
    public void testFieldsNotEmpty(){
        TradeOrder tradeOrder  = new TradeOrder();
        tradeOrderService.cancelTradeOrder(tradeOrder);
    }

    @Test
    public void testGetHistoryTradeOrdersByAccount(){
        Account currentAccount = accountService.getCurrentAccount();
        List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrdersByAccount();
        tradeOrders.forEach(tradeOrder -> {
            Assert.assertTrue(tradeOrder.getAccount().equals(currentAccount));
            Assert.assertTrue(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED)
                    || tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CANCELED)
            );
        });
    }


    @Test
    public void testGetHistoryTradeOrders(){
        List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders();
        tradeOrders.forEach(tradeOrder -> {
            Assert.assertTrue(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED)
                    || tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CANCELED)
            );
        });
    }

    @Test
    public void testLiveTradeOrders(){

        TradeOrder tradeOrder = new TradeOrder();
        tradeOrderService.createTradeOrder(tradeOrder);

        List<TradeOrder> tradeOrders = tradeOrderService.getLiveTradeOrders();
        Assert.assertNotNull(tradeOrders);
        Assert.assertTrue(tradeOrders.size() > 0);
    }

    @Test
    public void tetLiveTradeOrdersByAccount(){
        Account account = accountService.getCurrentAccount();
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrderService.createTradeOrder(tradeOrder);
        List<TradeOrder> tradeOrders =  tradeOrderService.getLiveTradeOrdersByAccount();
        Assert.assertNotNull(tradeOrders);
        Assert.assertTrue(tradeOrders.size() > 0);
    }

    @Test
    public void  testCreateTradeOrder(){
        TradeOrder tradeOrder = createTestTradeOrder();
        TradeOrder savedTradeOrder = tradeOrderRepository.getOne(tradeOrder.getId());
        Assert.assertNotNull(savedTradeOrder);
        Assert.assertTrue(savedTradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED));
    }

    private TradeOrder createTestTradeOrder(){
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setAmount(new BigDecimal("10.0"));
        tradeOrder.setDiamond(diamondService.getAvailable().stream().findFirst().get());
        tradeOrder.setAccount(accountService.getCurrentAccount());
        tradeOrder.setPrice(new BigDecimal("100.00"));
        tradeOrder.setTradeOrderType(TradeOrderType.BUY);
        tradeOrder =tradeOrderService.createTradeOrder(tradeOrder);
        return tradeOrder;
    }

    @Test
    public void testCancelTradeOrder(){
        TradeOrder tradeOrder = createTestTradeOrder();
        Assert.assertNotNull(tradeOrder);
        Assert.assertTrue(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED));
    }

    @Test
    public void testExecuteTradeOrders(){

    }

    @Test
    public void testCheckIfCanExecute(){

    }
}



