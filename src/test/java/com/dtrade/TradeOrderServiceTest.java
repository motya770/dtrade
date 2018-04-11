package com.dtrade;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TraderOrderStatus;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBookOrderService;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.ITradeOrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kudelin on 7/15/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TradeOrderServiceTest extends BaseTest {

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IBookOrderService bookOrderService;

    private SecurityContext setAccount(Account account){
        UserDetails principal = account;
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }

    /*
    @Test
    public void test(){

        Account account = accountService.createRealAccount("motya7702@gmail.com", "12345", "+79210827487", "USD");

        accountService.confirmRegistration(account.getGuid());

        SecurityContextHolder.setContext(setAccount(account));

        System.out.println("1233 234 " + tradeOrderService);

    }*/

    @Test
    public void testFindAll(){
        List<TradeOrder> tradeOrders = tradeOrderService.findAll();
        Assert.assertTrue(tradeOrders.size()>0);
    }


    private static int MAX_TRADES_COUNT = 1_000;
    private List<TradeOrder> createBuyOrderList(){
        List<TradeOrder> tradeOrders = new LinkedList<>();
        for(int j = 0; j<MAX_TRADES_COUNT; j++){
            TradeOrder tradeOrder = createTestBuyTradeOrder();
            tradeOrders.add(tradeOrder);
        }
        return tradeOrders;
    }

    private List<TradeOrder> createSellOrderList(){
        List<TradeOrder> tradeOrders = new LinkedList<>();
        for(int j = 0; j<MAX_TRADES_COUNT; j++){
            TradeOrder tradeOrder = createTestSellTradeOrder();
            tradeOrders.add(tradeOrder);
        }
        return tradeOrders;
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCalculateTradeOrders(){

      List<TradeOrder> buyOrders = createBuyOrderList();
      List<TradeOrder> sellOrders = createSellOrderList();

        System.out.println("START ");
        long start = System.currentTimeMillis();
      buyOrders.parallelStream().forEach((tradeOrder)->{
          bookOrderService.addNew(tradeOrder);
      });

      sellOrders.parallelStream().forEach(tradeOrder -> {
          bookOrderService.addNew(tradeOrder);
      });


        tradeOrderService.calculateTradeOrders();
        System.out.println("END " + (System.currentTimeMillis() - start));
    }

    @Test
    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testFieldsNotEmpty(){
        TradeOrder tradeOrder  =  createTestTradeOrder();
        tradeOrderService.cancelTradeOrder(tradeOrder);
    }


    @Transactional
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testGetHistoryTradeOrdersByAccount(){
        Account currentAccount = accountService.getCurrentAccount();
        Page<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrdersByAccount(0);
        List<TradeOrder> content = tradeOrders.getContent();

        content.forEach(tradeOrder -> {
            Assert.assertTrue(tradeOrder.getAccount().equals(currentAccount));
            Assert.assertTrue(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED)
                    || tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CANCELED)
            );
        });
    }

    @Test
    public void testGetHistoryTradeOrders(){
        Diamond diamond = diamondService.getAllAvailable("").stream().findFirst().get();

        List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamond);
        tradeOrders.forEach(tradeOrder -> {
            Assert.assertTrue(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED)
                    || tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CANCELED)
            );
        });
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testLiveTradeOrders(){

        TradeOrder tradeOrder = createTestTradeOrder();
        tradeOrderService.createTradeOrder(tradeOrder);

        List<TradeOrder> tradeOrders = tradeOrderService.getLiveTradeOrders();
        Assert.assertNotNull(tradeOrders);
        Assert.assertTrue(tradeOrders.size() > 0);
    }

    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Transactional
    public void tetLiveTradeOrdersByAccount(){
        Account account = accountService.getCurrentAccount();
        TradeOrder tradeOrder = createTestTradeOrder();
        tradeOrderService.createTradeOrder(tradeOrder);
        Page<TradeOrder> tradeOrders =  tradeOrderService.getLiveTradeOrdersByAccount(0);
        List<TradeOrder> content  = tradeOrders.getContent();
        content.forEach(tradeOrder1 -> {
            Assert.assertTrue(tradeOrder.getAccount().equals(account));
        });
        Assert.assertNotNull(content);
        Assert.assertTrue(content.size() > 0);
    }

    @Transactional
    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void  testCreateTradeOrder(){
        TradeOrder tradeOrder = createTestTradeOrder();
        TradeOrder savedTradeOrder = tradeOrderRepository.getOne(tradeOrder.getId());
        Assert.assertNotNull(savedTradeOrder);
        Assert.assertTrue(savedTradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED));
    }



    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testCancelTradeOrder(){
        TradeOrder tradeOrder = createTestTradeOrder();
        Assert.assertNotNull(tradeOrder);
        Assert.assertTrue(tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.CREATED));
    }


    //TODO check execution more precily
    @Transactional
    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testExecuteTradeOrders(){

       TradeOrder buyOrder = createTestBuyTradeOrder();
       TradeOrder sellOrder = createTestSellTradeOrder();

       org.springframework.data.util.Pair<TradeOrder, TradeOrder> pair = org.springframework.data.util.Pair.of(buyOrder, sellOrder);

       //tradeOrderService.executeTradeOrders(pair);
    }

    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testCheckIfCanExecute(){

        TradeOrder buyOrder = createTestBuyTradeOrder();
        TradeOrder sellOrder = createTestSellTradeOrder();
        org.springframework.data.util.Pair<TradeOrder, TradeOrder> pair = org.springframework.data.util.Pair.of(buyOrder, sellOrder);

         Assert.assertTrue(tradeOrderService.checkIfCanExecute(pair));
    }
}



