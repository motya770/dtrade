package com.dtrade;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.dtrade.model.tradeorder.TraderOrderStatus;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.repository.tradeorder.TradeOrderRepository;
import com.dtrade.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    private TransactionTemplate transactionTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }


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
        Page<TradeOrder> tradeOrders = tradeOrderService.findAll(0);
        Assert.assertTrue(tradeOrders.getContent().size()>0);
    }


    private static int MAX_TRADES_COUNT = 10;
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


    @Autowired
    private IBalanceService balanceService;

    private void updateBalances(Account account, BigDecimal balance){
        transactionTemplate.execute((TransactionStatus status)-> {
            Currency[] currencies = Currency.values();
            for(int i = 0; i < currencies.length; i++){
                Currency currency = currencies[i];

                BigDecimal minusBalance =  balanceService.getBalance(currency, account).getAmount().multiply(new BigDecimal("-1"));
                BigDecimal minusFrozen =  balanceService.getFrozen(currency, account).multiply(new BigDecimal("-1"));
                BigDecimal minusOpen = balanceService.getOpenSum(currency, account).multiply(new BigDecimal("-1"));

                balanceService.updateBalance(currency, account, minusBalance);
                balanceService.updateFrozenBalance(currency, account, minusFrozen);
                balanceService.updateOpen(currency, account, minusOpen);


                balanceService.updateBalance(currency, account, balance);

            }
            return account;
        });
    }

    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    @Test
    public void testCalculateTradeOrders() throws Exception{

        final Account account = accountService.getCurrentAccount();

        BigDecimal initialBalance = new BigDecimal("100000");
        updateBalances(account, initialBalance);

        Pair<List<TradeOrder>, List<TradeOrder>> pair = transactionTemplate.execute((TransactionStatus status)-> {
            List<TradeOrder> buyOrders = createBuyOrderList();
            List<TradeOrder> sellOrders = createSellOrderList();
            return Pair.of(buyOrders, sellOrders);
        });

        System.out.println("START ");
        long start = System.currentTimeMillis();
        /*
      pair.getFirst().parallelStream().forEach((tradeOrder)->{
          bookOrderService.addNew(tradeOrder);
      });

      pair.getSecond().parallelStream().forEach( tradeOrder -> {
          bookOrderService.addNew(tradeOrder);
      });*/

       // tradeOrderService.calculateTradeOrders();
        //System.out.println("END " + (System.currentTimeMillis() - start));


        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleWithFixedDelay(()->{

            try {
                Thread.sleep(60_000);
            }catch (Exception e){
                throw new TradeException("Some problem", e);
            }
            List<TradeOrder> buyTrades = pair.getFirst();
            List<TradeOrder> sellTrades = pair.getSecond();

            long buyExecuted = buyTrades.stream().map(tradeOrder -> tradeOrderRepository.findById(tradeOrder.getId()).get()
            ).filter( tradeOrder -> {
                System.out.println("status1: " + tradeOrder.getTraderOrderStatus());
                return tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED);

            }).count();

            long sellExecuted = sellTrades.stream().map(tradeOrder -> tradeOrderRepository.findById(tradeOrder.getId()).get())
            .filter( tradeOrder -> {
                System.out.println("status2: " + tradeOrder.getTraderOrderStatus());
               return tradeOrder.getTraderOrderStatus().equals(TraderOrderStatus.EXECUTED);
            }).count();

            System.out.println("BUY executed: " + buyExecuted);
            System.out.println("SELL executed: " + sellExecuted);

            System.out.println("TEST1");
            Account rereadAccount = accountRepository.findById(account.getId()).get();

            System.out.println("TEST2");

            System.out.println("BALANCE: " + rereadAccount.getBalances());

            Currency[] currencies = Currency.values();
            for(int i = 0; i < currencies.length; i++){
                Currency currency = currencies[i];

                BigDecimal balance =  balanceService.getBalance(currency, account).getAmount();
                BigDecimal frozen =  balanceService.getFrozen(currency, account);
                BigDecimal open = balanceService.getOpenSum(currency, account);

                Assert.assertEquals(0, initialBalance.compareTo(balance));
                Assert.assertEquals(0,  BigDecimal.ZERO.compareTo(frozen));
                Assert.assertEquals(0,  BigDecimal.ZERO.compareTo(open));
            }

            System.out.println("TEST3");
            System.out.println("Finished simulation...");

        }, 10,  100, TimeUnit.SECONDS);
        try {
            executorService.awaitTermination(120, TimeUnit.SECONDS);
        }catch (Exception e) {
            e.printStackTrace();
        }
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

        List<TradeOrder> tradeOrders = tradeOrderService.getHistoryTradeOrders(diamond.getId());
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

        Account account = accountService.getCurrentAccount();
        BigDecimal initialBalance = new BigDecimal("100000");
        updateBalances(account, initialBalance);


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
    /*
    @Transactional
    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testExecuteTradeOrders(){

       TradeOrder buyOrder = createTestBuyTradeOrder();
       TradeOrder sellOrder = createTestSellTradeOrder();

       Pair<TradeOrder, TradeOrder> pair = Pair.of(buyOrder, sellOrder);
       tradeOrderService.executeTradeOrders(pair);
    }*/

    /*
    @Test
    @WithUserDetails(value = F_DEFAULT_TEST_ACCOUNT)
    public void testCheckIfCanExecute(){

        TradeOrder buyOrder = createTestBuyTradeOrder();
        TradeOrder sellOrder = createTestSellTradeOrder();
        org.springframework.data.util.Pair<TradeOrder, TradeOrder> pair = org.springframework.data.util.Pair.of(buyOrder, sellOrder);

        Assert.assertTrue(tradeOrderService.checkIfCanExecute(pair));
    }*/
}



