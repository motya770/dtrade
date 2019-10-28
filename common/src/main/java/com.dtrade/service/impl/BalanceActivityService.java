package com.dtrade.service.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.Const;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.balanceactivity.BalanceActivityCreator;
import com.dtrade.model.balanceactivity.BalanceActivityType;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.repository.balanceactivity.BalanceActivityRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kudelin on 12/4/16.
 */
@Slf4j
@Service
//ч@Transactional
public class BalanceActivityService implements IBalanceActivityService {

    @Autowired
    private BalanceActivityRepository balanceActivityRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private BalanceService balanceService;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    /*
    private ScheduledExecutorService executor;

    @EventListener(ContextRefreshedEvent.class)
    public void init(){
         executor = Executors.newScheduledThreadPool(1);
         executor.scheduleAtFixedRate(()->execute(), 1000,100, TimeUnit.MILLISECONDS);
    }

    private TransactionTemplate transactionTemplate;

    private ConcurrentLinkedDeque<BalanceUpdater> deque = new ConcurrentLinkedDeque<>();

    private void addUpdater(Balance balance){
        BalanceUpdater updater = new BalanceUpdater();
        updater.setBalance(balance);
        deque.add(updater);
    }

    private int size = 2_000;
    private void execute(){
        try {
            log.debug("dec: " + deque.size());
            for (int i = 0; i < size; i++) {
                BalanceUpdater updater = deque.poll();
                if (updater == null) {
                    return;
                }
                Balance balance = updater.getBalance();
                log.debug("balance update: " + balance.getId() + " " + balance.getCurrency() + " " + balance.getAmount());
                balanceService.updateBalance(balance);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }*/


    @Override
    public List<BalanceActivity> getDeposits(Account account) {
        return  balanceActivityRepository.findAllByBalanceActivityTypeAndAccount(BalanceActivityType.DEPOSIT, account);
    }

    @Override
    public List<BalanceActivity> getWithdraws(Account account) {
        return balanceActivityRepository.findAllByBalanceActivityTypeAndAccount(BalanceActivityType.WITHDRAW, account);
    }

    @Override
    public Page<BalanceActivity> findAll(Integer pageNumber) {
        if(pageNumber==null){
            pageNumber = 0;
        }
        return balanceActivityRepository.findAll(PageRequest.of(pageNumber, 20, Sort.Direction.DESC, Const.F_ID));
    }

    @Override
    public Page<BalanceActivity> getAccountBalanceActivities(Integer pageInteger) {
        Account account = accountService.getStrictlyLoggedAccount();
        return balanceActivityRepository.getByAccount(account, new PageRequest(pageInteger, 10));
    }


    @Override
    public BalanceActivity createWithdrawBalanceActivity(CoinPayment coinPayment) {

        if(coinPayment.getAccount()==null){
            throw new TradeException("Account is null");
        }

        Account account = accountService.find(coinPayment.getAccount().getId());
        BigDecimal amount = coinPayment.getInWithdrawRequest().getAmountCoin();

        BigDecimal withdrawAmount =  amount.multiply(new BigDecimal("-1"));
        Currency currency = Currency.valueOf(coinPayment.getInWithdrawRequest().getCurrencyCoin());


        Balance balance = balanceService.getBalance(currency, account);
        if(balance.getActualBalance().compareTo(amount)<0){
            throw new TradeException("Can't withdraw because actual amount less than amount: " +
                    balance +  ", withdraw amount: " + amount);
        }

        balanceService.updateBalance(currency, account, withdrawAmount);
        balanceService.unfreezeAmount(currency, account, withdrawAmount);

        BalanceActivity ba = new BalanceActivity();
        ba.setAccount(account);
        ba.setBalanceActivityType(BalanceActivityType.WITHDRAW);
        ba.setAmount(withdrawAmount);
        ba.setSum(withdrawAmount);
        ba.setCreateDate(System.currentTimeMillis());

        //TODO ajust USD BTC ETH
        ba.setCurrency(currency);
        ba.setBalanceSnapshot(balanceService.getBalance(currency, account).getAmount());
        return balanceActivityRepository.save(ba);
    }

    @Override
    public BalanceActivity createDepositBalanceActivity(CoinPayment coinPayment) {

        if(coinPayment.getAccount()==null){
            throw new TradeException("Account is null");
        }

        Account account = accountService.find(coinPayment.getAccount().getId());
        Currency currency = Currency.valueOf(coinPayment.getDepositRequest().getCurrencyCoin());
        balanceService.updateBalance(currency, account,  coinPayment.getDepositRequest().getAmountCoin());

        BigDecimal amount = coinPayment.getDepositRequest().getAmountCoin();
        BalanceActivity ba = new BalanceActivity();
        ba.setAccount(account);
        ba.setBalanceActivityType(BalanceActivityType.DEPOSIT);
        ba.setAmount(amount);
        ba.setSum(amount);
        ba.setCreateDate(System.currentTimeMillis());
        ba.setCurrency(currency);
        ba.setBalanceSnapshot(balanceService.getBalance(currency, account).getAmount());

        return balanceActivityRepository.save(ba);
    }




    @Override
    @Transactional(noRollbackFor = NotEnoughMoney.class)
    public  void createBalanceActivities(BalanceActivityCreator balanceActivityCreator) {

        Account buyer = balanceActivityCreator.getBuyer();
        Account seller = balanceActivityCreator.getSeller();
        TradeOrder buyOrder = balanceActivityCreator.getBuyOrder();
        TradeOrder sellOrder = balanceActivityCreator.getSellOrder();
        BigDecimal realAmount = balanceActivityCreator.getRealAmount();
        BigDecimal price = balanceActivityCreator.getPrice();

        long start = System.currentTimeMillis();
        Diamond diamond = sellOrder.getDiamond();
        Currency currency = diamond.getCurrency();
        Currency baseCurrency = diamond.getBaseCurrency();
        BigDecimal sum = realAmount.multiply(price);
        BigDecimal minusSum = sum.multiply(new BigDecimal("-1"));

       // transactionTemplate.execute((status)->{
           // BTC/USD - transfer USD
            Balance baseSellerBalance =  balanceService.getBalance(baseCurrency, seller);
            createBaseSellerBalanceActivity(seller, sellOrder, realAmount, price, baseCurrency, sum, baseSellerBalance);

            log.debug("ba1: " + (System.currentTimeMillis()-start));

            baseSellerBalance.setAmount(baseSellerBalance.getAmount().add(sum));
            //addUpdater(baseSellerBalance);
            balanceService.updateBalance(baseSellerBalance);
        //    return null;
        //});

        //transactionTemplate.execute((status)->{

            Balance baseBuyerBalance = balanceService.getBalance(baseCurrency, buyer);

            log.debug("ba2: " + (System.currentTimeMillis()-start));

            createBaseBuyerBalanceActivity(buyer, buyOrder, realAmount, price, baseCurrency, baseBuyerBalance, minusSum);

            log.debug("ba3: " + (System.currentTimeMillis()-start));

            baseBuyerBalance.setAmount(baseBuyerBalance.getAmount().add(minusSum));
            //addUpdater(baseBuyerBalance);

            balanceService.updateBalance(baseBuyerBalance);
         //   return null;
        //});

        //log.debug("ba4: " + (System.currentTimeMillis()-start));
        // BTC/USD transfer BTC

        //transactionTemplate.execute((status)-> {
                    Balance sellerBalance = balanceService.getBalance(currency, seller);
                    createSellerBalanceActivity(seller, sellOrder, realAmount, price, currency, sum, sellerBalance);

                    log.debug("ba5: " + (System.currentTimeMillis() - start));

                    sellerBalance.setAmount(sellerBalance.getAmount().subtract(realAmount));
                    //addUpdater(sellerBalance);
                    balanceService.updateBalance(sellerBalance);

                    //log.debug("ba6: " + (System.currentTimeMillis() - start));
          //          return null;
        //});


        //transactionTemplate.execute((status)-> {
            Balance buyerBalance = balanceService.getBalance(currency, buyer);

            createBuyerBalanceActivity(buyer, buyOrder, realAmount, price, currency, sum, buyerBalance);

            buyerBalance.setAmount(buyerBalance.getAmount().add(realAmount));
            // addUpdater(buyerBalance);

            balanceService.updateBalance(buyerBalance);

        //    return null;
       // });

       // transactionTemplate.execute((status)-> {
            balanceService.updateOpenSum(sellOrder, seller, minusSum, realAmount.multiply(new BigDecimal("-1")));
            balanceService.updateOpenSum(buyOrder, buyer, minusSum, realAmount.multiply(new BigDecimal("-1")));
         //   return null;
         //});
            log.debug("ba7: " + (System.currentTimeMillis() - start));
    }

    private void createBuyerBalanceActivity(Account buyer, TradeOrder buyOrder, BigDecimal realAmount, BigDecimal price, Currency currency, BigDecimal sum, Balance buyerBalance) {
        BalanceActivity buyerBa = new BalanceActivity();
        buyerBa.setAccount(buyer);
        buyerBa.setAmount(realAmount);
        buyerBa.setPrice(price);
        buyerBa.setSum(sum);
        buyerBa.setBalanceActivityType(BalanceActivityType.BUY);
        buyerBa.setCurrency(currency);
        buyerBa.setBalanceSnapshot(buyerBalance.getAmount());
        buyerBa.setBuyOrder(buyOrder);
        buyerBa.setCreateDate(System.currentTimeMillis());
        buyerBa.setBalance(buyerBalance);
        balanceActivityRepository.save(buyerBa);
    }

    private void createSellerBalanceActivity(Account seller, TradeOrder sellOrder, BigDecimal realAmount, BigDecimal price, Currency currency, BigDecimal sum, Balance sellerBalance) {
        BalanceActivity sellerBa = new BalanceActivity();
        sellerBa.setAccount(seller);
        sellerBa.setAmount(realAmount.multiply(new BigDecimal("-1")));
        sellerBa.setPrice(price);
        sellerBa.setSum(sum);
        sellerBa.setBalanceActivityType(BalanceActivityType.SELL);
        sellerBa.setCurrency(currency);
        sellerBa.setBalanceSnapshot(sellerBalance.getAmount());
        sellerBa.setSellOrder(sellOrder);
        sellerBa.setCreateDate(System.currentTimeMillis());
        sellerBa.setBalance(sellerBalance);
        balanceActivityRepository.save(sellerBa);
    }

    private void createBaseBuyerBalanceActivity(Account buyer, TradeOrder buyOrder, BigDecimal realAmount, BigDecimal price, Currency baseCurrency, Balance baseBuyerBalance, BigDecimal minusSum) {
        BalanceActivity baseBuyerBa = new BalanceActivity();
        baseBuyerBa.setBalanceActivityType(BalanceActivityType.BUY);
        baseBuyerBa.setAccount(buyer);
        baseBuyerBa.setAmount(realAmount);
        baseBuyerBa.setPrice(price);
        baseBuyerBa.setSum(minusSum);
        baseBuyerBa.setCreateDate(System.currentTimeMillis());
        baseBuyerBa.setBuyOrder(buyOrder);
        baseBuyerBa.setBalanceSnapshot(baseBuyerBalance.getAmount());
        baseBuyerBa.setCurrency(baseCurrency);
        baseBuyerBa.setCreateDate(System.currentTimeMillis());
        baseBuyerBa.setBalance(baseBuyerBalance);
        balanceActivityRepository.save(baseBuyerBa);
    }

    private void createBaseSellerBalanceActivity(Account seller, TradeOrder sellOrder, BigDecimal realAmount, BigDecimal price, Currency baseCurrency, BigDecimal sum, Balance baseSellerBalance) {
        BalanceActivity baseSellerBa = new BalanceActivity();
        baseSellerBa.setAccount(seller);
        baseSellerBa.setAmount(realAmount);
        baseSellerBa.setPrice(price);
        baseSellerBa.setSum(sum);
        baseSellerBa.setBalanceActivityType(BalanceActivityType.SELL);
        baseSellerBa.setCurrency(baseCurrency);
        baseSellerBa.setBalanceSnapshot(baseSellerBalance.getAmount());
        baseSellerBa.setSellOrder(sellOrder);
        baseSellerBa.setCreateDate(System.currentTimeMillis());
        baseSellerBa.setBalance(baseSellerBalance);
        balanceActivityRepository.save(baseSellerBa);
    }

}
