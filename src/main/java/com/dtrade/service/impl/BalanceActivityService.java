package com.dtrade.service.impl;

import com.dtrade.exception.NotEnoughMoney;
import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.balanceactivity.BalanceActivityType;
import com.dtrade.model.coinpayment.CoinPayment;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.balanceactivity.BalanceActivityRepository;
import com.dtrade.service.IAccountService;
import com.dtrade.service.IBalanceActivityService;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

/**
 * Created by kudelin on 12/4/16.
 */
@Service
@Transactional
public class BalanceActivityService implements IBalanceActivityService {

    @Autowired
    private BalanceActivityRepository balanceActivityRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private BalanceService balanceService;

    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager){
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    public Page<BalanceActivity> findAll(Integer pageNumber) {
        if(pageNumber==null){
            pageNumber = 0;
        }
        return balanceActivityRepository.findAll(new PageRequest(pageNumber, 20));
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

        BigDecimal withdrawAmount =  coinPayment.getInWithdrawRequest().getAmountCoin().multiply(new BigDecimal("-1"));
        Currency currency = Currency.valueOf(coinPayment.getInWithdrawRequest().getCurrencyCoin());

        balanceService.updateBalance(currency, account, withdrawAmount);
        balanceService.unfreezeAmount(currency, account, withdrawAmount);

        BalanceActivity ba = new BalanceActivity();
        ba.setAccount(account);
        ba.setBalanceActivityType(BalanceActivityType.WITHDRAW);
        ba.setAmount(withdrawAmount);
        ba.setCreateDate(System.currentTimeMillis());

        //TODO ajust USD BTC ETH
       // ba.setCurrency(Curre);
       // ba.setBalanceSnapshot(balanceService.getBa);
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

        BalanceActivity ba = new BalanceActivity();
        ba.setAccount(account);
        ba.setBalanceActivityType(BalanceActivityType.DEPOSIT);
        ba.setAmount(coinPayment.getDepositRequest().getAmountUsd());
        ba.setCreateDate(System.currentTimeMillis());
        ba.setCurrency(currency);
        ba.setBalanceSnapshot(balanceService.getBalance(currency, account).getAmount());

        return balanceActivityRepository.save(ba);
    }

    @Override
    @Transactional(noRollbackFor = NotEnoughMoney.class)
    public  void createBalanceActivities(Account buyer,
                                                                          Account seller,
                                                                          TradeOrder buyOrder,
                                                                          TradeOrder sellOrder,
                                                                          BigDecimal realAmount, BigDecimal price) {

       // BTC/USD - transfer USD
        Diamond diamond = sellOrder.getDiamond();
        Currency currency = diamond.getCurrency();
        Currency baseCurrency = diamond.getBaseCurrency();

        BigDecimal sum = realAmount.multiply(price);

        Balance baseSellerBalance =  balanceService.getBalance(baseCurrency, seller);

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

        baseSellerBalance.setAmount(baseSellerBalance.getAmount().add(sum));
        balanceService.updateBalance(baseSellerBalance);


        Balance baseBuyerBalance = balanceService.getBalance(baseCurrency, buyer);

        BigDecimal minusSum = sum.multiply(new BigDecimal("-1"));

        BalanceActivity baseBuyerBa = new BalanceActivity();
        baseBuyerBa.setBalanceActivityType(BalanceActivityType.BUY);
        baseBuyerBa.setAccount(buyer);
        baseBuyerBa.setAmount(minusSum);
        baseBuyerBa.setCreateDate(System.currentTimeMillis());
        baseBuyerBa.setBuyOrder(buyOrder);
        baseBuyerBa.setBalanceSnapshot(baseBuyerBalance.getAmount());
        baseBuyerBa.setCurrency(baseCurrency);
        baseBuyerBa.setCreateDate(System.currentTimeMillis());
        baseBuyerBa.setBalance(baseBuyerBalance);
        balanceActivityRepository.save(baseBuyerBa);

        baseBuyerBalance.setAmount(baseBuyerBalance.getAmount().add(minusSum));
        balanceService.updateBalance(baseBuyerBalance);


        // BTC/USD transfer BTC

        Balance sellerBalance = balanceService.getBalance(currency, seller);

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

        sellerBalance.setAmount(sellerBalance.getAmount().subtract(realAmount));
        balanceService.updateBalance(sellerBalance);


        Balance buyerBalance = balanceService.getBalance(currency, buyer);

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

        buyerBalance.setAmount(buyerBalance.getAmount().add(realAmount));
        balanceService.updateBalance(buyerBalance);



        /*
        System.out.println("updating for : " + buyOrder.getDiamond().getName());
        Currency currency = null;

        BigDecimal buyerBalance = balanceService.getBalance(buyOrder.getDiamond().getCurrency(), buyer).getAmount();

        if(buyerBalance.compareTo(cash) < 0){
            throw new NotEnoughMoney();
        }

        BigDecimal minusCash = cash.multiply(new BigDecimal("-1"));

        BalanceActivity buyerActivity = new BalanceActivity();
        buyerActivity.setBalanceActivityType(BalanceActivityType.BUY);
        buyerActivity.setAccount(buyer);
        buyerActivity.setAmount(minusCash);
        buyerActivity.setCreateDate(System.currentTimeMillis());
        buyerActivity.setBuyOrder(buyOrder);
        buyerActivity.setBalanceSnapshot(buyerBalance);
        buyerActivity.setCurrency(currency);

        BalanceActivity sellerActivity = new BalanceActivity();
        sellerActivity.setBalanceActivityType(BalanceActivityType.SELL);
        sellerActivity.setAccount(seller);
        sellerActivity.setAmount(cash);
        sellerActivity.setCreateDate(System.currentTimeMillis());
        sellerActivity.setSellOrder(sellOrder);
        sellerActivity.setCurrency(currency);

        BigDecimal sellerBalance = balanceService.getBalance(currency, seller).getAmount();
        sellerActivity.setBalanceSnapshot(sellerBalance);

        balanceActivityRepository.save(sellerActivity);
        balanceActivityRepository.save(buyerActivity);

        balanceService.updateBalance(currency, buyer, minusCash);
        balanceService.updateBalance(currency, seller, cash);
        balanceService.updateOpenSum(buyOrder, buyer, cash.multiply(TradeOrderService.MINUS_ONE_VALUE));

        return org.springframework.data.util.Pair.of(buyerActivity, sellerActivity);*/
    }


    /*
    @Override
    public BalanceActivity createBuyBalanceActivity(TradeOrder tradeOrder) {

        if(!tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)){
            return null;
        }

        BigDecimal cash = tradeOrder.getPrice().multiply(tradeOrder.getAmount());

        Account account = tradeOrder.getAccount();
        account = accountService.find(account.getId());

        //buyer don't have enough money

        BigDecimal balance = balanceService.getBalance(tradeOrder.getDiamond().getCurrency(), account).getAmount();
        if(balance.compareTo(cash) < 0){
            throw new NotEnoughMoney();
        }

        BigDecimal minusCash = cash.multiply(new BigDecimal("-1"));
        balanceService.updateBalance(tradeOrder.getDiamond().getCurrency(), account, minusCash);

        BalanceActivity activity = new BalanceActivity();
        activity.setBalanceActivityType(BalanceActivityType.BUY);
        activity.setAccount(account);
        activity.setAmount(minusCash);
        activity.setCreateDate(System.currentTimeMillis());
        activity.setBuyOrder(tradeOrder);
        activity.setBalanceSnapshot(balance);
        activity.setCurrency(tradeOrder.getDiamond().getCurrency());
        balanceActivityRepository.save(activity);

        return activity;
    }*/

    /*

    @Override
    public void createBalanceActivity(Account buyer, Account seller, Diamond diamond, BigDecimal price) throws TradeException{

        BigDecimal balance = balanceService.getBalance(diamond.getCurrency(), buyer).getAmount();

        //TODO bug(?)
        if(balance.doubleValue() < diamond.getPrice().doubleValue()){
            throw new TradeException("Not enough money for this operation.");
        }

        BigDecimal moneyToTake = price.multiply(new BigDecimal("-1"));
        balanceService.updateBalance(diamond.getCurrency(), buyer, moneyToTake);

        diamondService.checkDiamondOwnship(seller, diamond);

        BigDecimal moneyToGive = diamond.getPrice();
        balanceService.updateBalance(diamond.getCurrency(), buyer, moneyToTake);

        BalanceActivity buyerActivity = new BalanceActivity();
        buyerActivity.setBalanceActivityType(BalanceActivityType.BUY);
        buyerActivity.setAccount(buyer);
        buyerActivity.setAmount(moneyToTake);
        buyerActivity.setCreateDate(System.currentTimeMillis());
        buyerActivity.setCurrency(diamond.getCurrency());

        BalanceActivity sellerActivity = new BalanceActivity();
        sellerActivity.setBalanceActivityType(BalanceActivityType.SELL);
        sellerActivity.setAccount(seller);
        sellerActivity.setAmount(moneyToGive);
        sellerActivity.setCreateDate(System.currentTimeMillis());
        sellerActivity.setCurrency(diamond.getCurrency());

        balanceActivityRepository.save(sellerActivity);
        balanceActivityRepository.save(buyerActivity);
    }*/

}
