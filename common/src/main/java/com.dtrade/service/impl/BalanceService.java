package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.balance.BalancePos;
import com.dtrade.model.balance.DepositWithdraw;
import com.dtrade.model.balanceactivity.BalanceActivity;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.quote.Quote;
import com.dtrade.model.quote.SimpleQuote;
import com.dtrade.model.tradeorder.TradeOrder;
import com.dtrade.model.tradeorder.TradeOrderDirection;
import com.dtrade.repository.balance.BalanceRepository;
import com.dtrade.service.*;

//import com.hazelcast.core.HazelcastInstance;
//import com.hazelcast.core.IMap;

import com.dtrade.utils.UtilsHelper;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BalanceService  implements IBalanceService{

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private IBalanceActivityService balanceActivityService;

    @Autowired
    private IAccountService accountService;

   // @Autowired
   // private HazelcastInstance hazelcastInstance;

    @Transactional
    @Override
    public Balance updateRoboBalances(Currency currency, Account account){

        if(!account.isRoboAccount()){
             throw new TradeException("This is not robo account: " + account.getMail());
        }

        Balance balance = null;
        BigDecimal b = getActualBalance(currency, account);
        if(b.compareTo(BigDecimal.ZERO) <= 0) {
            balance = updateBalance(currency, account, new BigDecimal("100000"));
        }
        return balance;
    }

    @Override
    public Balance unfreezeAmount(Currency currency, Account account, BigDecimal amount) {
        return updateFrozenBalance(currency, account, amount.multiply(new BigDecimal("-1")));
    }

    @Override
    public Balance freezeAmount(Currency currency, Account account, BigDecimal amount) {
        return updateFrozenBalance(currency, account, amount);
    }

    @Override
    public void updateOpenSum(TradeOrder tradeOrder, Account account, BigDecimal sum, BigDecimal amount) {
        if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.BUY)) {
            updateOpen(tradeOrder.getDiamond().getBaseCurrency(), account, sum);
        }else if(tradeOrder.getTradeOrderDirection().equals(TradeOrderDirection.SELL)) {
            updateOpen(tradeOrder.getDiamond().getCurrency(), account, amount);
        }
    }

    @Override
    public Balance updateOpen(Currency currency, Account account, BigDecimal amount) {

        Balance balance = getBalance(currency, account);
        balance.setOpen(balance.getOpen().add(amount));
        return updateBalance(balance);
        //return balanceRepository.save(balance);
    }

    @Override
    public Balance updateFrozenBalance(Currency currency, Account account, BigDecimal amount){
        Balance balance = getBalance(currency, account);
        balance.setFrozen(balance.getFrozen().add(amount));
        return updateBalance(balance);
        //return balanceRepository.save(balance);
    }


    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private IBookOrderServiceProxy bookOrderServiceProxy;

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private IQuotesService quotesService;

    //TODO refactor it, pls
    @Override
    public List<BalancePos> getBalancesByAccount(Account account){
        List<Balance> balances =  account.getBalances();
        List<BalancePos> balancePosList = new ArrayList<>();

        balances.forEach(balance -> {
            BalancePos balancePos = new BalancePos();
            BeanUtils.copyProperties(balance, balancePos);

            if(!balance.getCurrency().equals(Currency.USD)){
                Diamond diamond =  diamondService.getDiamondByCurrency(balance.getCurrency());
                SimpleQuote simpleQuote = bookOrderServiceProxy.getSpread(diamond).block();

                BigDecimal avgPrice = tradeOrderService.getAverageTradeOrderPrice(diamond, account);

                BigDecimal totalPositionSum = tradeOrderService.getTotalPositionSum(diamond, account);

                BigDecimal bidPrice = simpleQuote.getBid();
                BigDecimal equity = balance.getAmount().multiply(bidPrice);

                // totalPositionSum / sellSum = 100 / x;
                // x = sellSum

                BigDecimal generalProfitPercent = null;
                if(totalPositionSum.compareTo(BigDecimal.ZERO)==0){
                    generalProfitPercent = BigDecimal.ZERO;
                }else {
                    BigDecimal percent  = equity.multiply(new BigDecimal("100")).divide(totalPositionSum, RoundingMode.HALF_UP);
                    generalProfitPercent = percent.subtract(new BigDecimal("100"));
                }

                BigDecimal todayPositionSum = tradeOrderService.getTodayPositionSum(diamond, account);
                BigDecimal untilTodayPositionAmount = tradeOrderService.getUntilTodayPositionAmount(diamond, account);
                Quote quote = quotesService.getLastQoute(diamond, UtilsHelper.getTodayStart());
                BigDecimal todayStartPrice = quote.getBid();

                BigDecimal todayStart  = todayStartPrice.multiply(untilTodayPositionAmount);
                BigDecimal todayProfit = equity.subtract(todayPositionSum).subtract(todayStart);

                BigDecimal todayProfitPercent = null;
                        if(todayStart.compareTo(BigDecimal.ZERO)==0){
                            todayProfitPercent = BigDecimal.ZERO;
                        }else {
                            todayProfitPercent= todayProfit.multiply(new BigDecimal("100")).divide(todayStart, RoundingMode.HALF_UP);
                        }

                balancePos.setSellSum(equity);
                balancePos.setAvgPrice(avgPrice);
                balancePos.setGeneralProfit(equity.subtract(totalPositionSum));
                balancePos.setGeneralProfitPercent(generalProfitPercent);
                balancePos.setTodayProfit(todayProfit);
                balancePos.setTodayProfitPercent(todayProfitPercent);

            }else {
                balancePos.setSellSum(balance.getAmount());
            }

            balancePosList.add(balancePos);
        });
        return balancePosList;
    }

    @Override
    public DepositWithdraw getDepositWithdraw() {
        //TODO currently only USD supported
        Account account = accountService.getStrictlyLoggedAccount();
        List<BalanceActivity> deposits = balanceActivityService.getDeposits(account);

        BigDecimal dep =  deposits.stream().filter(ba->ba.getCurrency().equals(Currency.USD))
                .map(BalanceActivity::getSum).reduce(BigDecimal.ZERO, BigDecimal::add);

        List<BalanceActivity> withdraws = balanceActivityService.getWithdraws(account);

        BigDecimal with = withdraws.stream().filter(ba->ba.getCurrency().equals(Currency.USD))
                .map(BalanceActivity::getSum).reduce(BigDecimal.ZERO, BigDecimal::add);

        DepositWithdraw depositWithdraw = new DepositWithdraw();
        depositWithdraw.setDeposit(dep);
        depositWithdraw.setWithdraw(with);
        return depositWithdraw;
    }

    @Override
    public List<Currency> getBaseCurrencies(){
        return Arrays.stream(Currency.values()).filter(c->c.isBaseCurrency()).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    @Override
    public List<Balance> getBaseBalancesByAccount(Account account){
        return balanceRepository.getBaseBalancesByAccount(account, getBaseCurrencies());
    }

    @Override
    @Transactional
    public Balance updateBalance(Balance balance) {

        Balance savedBalance = balanceRepository.save(balance);
        return savedBalance;

//        IMap<Long, Long> balancesMap = hazelcastInstance.getMap("balancesMap");
//        Long balanceId = balance.getId();
//        //create
//        if(balanceId==null){
//            balance =  balanceRepository.save(balance);
//            balancesMap.put(balance.getId(), balance.getId());
//            return balance;
//        //update
//        }else {
//
//            if (!balancesMap.isLocked(balanceId)) {
//
//                balancesMap.put(balanceId, balanceId);
//                balancesMap.lock(balanceId);
//
//                Balance savedBalance = balanceRepository.save(balance);
//
//                balancesMap.unlock(balanceId);
//
//                return savedBalance;
//
//            } else {
//                throw new TradeException("Can't update current balance because its is locked" + balance.getId());
//            }
//        }
    }

    @Override
    @Transactional
    public Balance updateBalance(Currency currency, Account account, BigDecimal addedValue) {
        Balance balance = getBalance(currency, account);
        logger.info("b1 : " + balance);
        balance.setAmount(balance.getAmount().add(addedValue));
        logger.info("b2 : " + balance);
        return updateBalance(balance);
    }

    @Override
    public BigDecimal getOpenSum(Currency currency, Account account) {
       return getBalance(currency, account).getOpen();
    }

    @Override
    public BigDecimal getFrozen(Currency currency, Account account){
       Balance balance = getBalance(currency, account);
       return balance.getFrozen();
    }

    private Balance createBalance(Account account, Currency currency) {

        if(account==null){
            throw new TradeException("Account is null. Can't create balance");
        }

        if(currency==null){
            throw new TradeException("Currency is null for " + account.getMail());
        }

        Balance balance = new Balance();
        balance.setAmount(BigDecimal.ZERO);
        balance.setFrozen(BigDecimal.ZERO);
        balance.setCurrency(currency);
        balance.setOpen(BigDecimal.ZERO);
        balance.setAccount(account);
        balance = balanceRepository.save(balance);
        return balance;
    }

    @Override
    public BigDecimal getActualBalance(Currency currency, Account account) {
        Balance balance = getBalance(currency, account);
        return balance.getDTO().getBalance();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    @Override
    public Balance getBalance(Currency currency, Account account){

        try {

            if (currency == null) {
                throw new TradeException("Currency not defined.");
            }

            Balance balance = balanceRepository.getBalance(account, currency);
            if (balance == null) {
                balance = createBalance(account, currency);
            }
            return balance;
        }catch (Throwable e){
            logger.error("erroor {}", e);
        }
        return null;
    }
}
