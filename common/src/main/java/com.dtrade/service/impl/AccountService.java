package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.account.AccountDTO;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.account.AccountRepository;
import com.dtrade.service.*;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by kudelin on 8/24/16.
 */
@Service
@Transactional
public class AccountService implements IAccountService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private ITradeOrderService tradeOrderService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IMailService mailService;

    @Autowired
    private IBalanceService balanceService;



    @Override
    public Account find(Long accountId) {
        return accountRepository.findById(accountId).get();
    }

    @Override
    public Account enable(Long accountId) {
        return changeEnablement(accountId, true);
    }

    @Override
    public Account disable(Long accountId) {
        return changeEnablement(accountId, false);
    }

    @Override
    public Account findByReferral(String referral) {
        return accountRepository.findByReferral(referral);
    }

    @Override
    public AccountDTO createReferalAccount(String mail, String ref) {
        Account account = createRealAccount(mail, "demo1345", null, null);

        //adding to referral account
        if(!StringUtils.isEmpty(ref)) {
            Account referalAccount = accountRepository.findByReferral(ref);
            if(referalAccount!=null){
                referalAccount.setReferredCount(referalAccount.getReferredCount() + 1);
                accountRepository.save(referalAccount);
            }
        }

        mailService.sendReferralMail(account);
        account = accountRepository.save(account);
        balanceService.updateBalance(Currency.USD, account, new BigDecimal("10000"));

        //login(account);
        return getAccountDTO(account);
    }

    @Override
    public String getRoboAccountMail(Diamond diamond, int rand){
        String simpleName = diamond.getName().replace("/", "");
        return  "testAccount" + simpleName + rand  + "@gmail.com";
    }

    @Override
    public void checkCurrentAccount(Account account) throws TradeException {
        if(account==null){
            throw new TradeException("Empty account");
        }
        Account currentAccount = getStrictlyLoggedAccount();

        if(!account.getId().equals(currentAccount.getId())){
            throw new TradeException("Passed account is not current");
        }
    }

    @Override
    public Account getStrictlyLoggedAccount()  {
        Account account = this.getCurrentAccount();
        if(account==null){
            throw new TradeException("You should be logged in.");
        }
        return account;
    }


    @Override
    public Account getCurrentAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            return null;
        }

        Object principal = authentication.getPrincipal();
        if(principal instanceof Account){
            Account account = (Account) principal;
            account = accountRepository.findById(account.getId()).get();
            return account;
        }

        return null;
    }

    private Account changeEnablement(Long accountId, boolean enabled) {
        Account account = accountRepository.findById(accountId).get();
        account.setEnabled(enabled);
        accountRepository.save(account);
        return account;
    }

    @Override
    public Page<Account> findAll(Integer pageNumber) {
        if(pageNumber==null){
            pageNumber = 0;
        }
        return accountRepository.findAll(new PageRequest(pageNumber, 20));
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        UserDetails account = accountRepository.findByMail(mail);
        if(account==null){
            throw new UsernameNotFoundException("Not registered");
        }
        return account;
    }

    @Override
    public Account confirmRegistration(String guid) throws TradeException {

        Account account = accountRepository.findAccountByGuidAndConfirmed(guid, false);

        if (account == null) {
            throw new TradeException("Account with this guid " + guid + " not found (or already confirmed)!");
        }

        account.setConfirmed(true);

        accountRepository.save(account);
        return account;
    }

    @Override
    public Account cancelRegistration(String guid) throws TradeException {
        Account account = accountRepository.findAccountByGuidAndConfirmed(guid, false);

        if (account == null) {
            throw new TradeException("Account with this guid " + guid + " not found (or already confirmed)!");
        }

        account.setCanceled(true);

        accountRepository.save(account);
        return account;
    }

    private String generateReferral(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('A', 'Z').build();
        return generator.generate(6);
    }

    @Override
    public Account createRealAccount(String login, String pwd, String phone, String currency) throws TradeException {
        Account account = buildAccount(login, pwd, phone, currency);
        //TODO remove after smpt
        account.setConfirmed(true);
        account.setEnabled(true);

        String ref = generateReferral();
        account.setReferral(ref);

        Account checkAccount =  accountRepository.findByMail(account.getMail());
        if(checkAccount!=null){
            throw new TradeException("Account with mail: " + account.getMail() + " already exists.");
        }

        Account saved =  accountRepository.save(account);

        balanceService.getBaseCurrencies().forEach(c -> {
            Balance balance = new Balance();
            balance.setAmount(BigDecimal.ZERO);
            balance.setFrozen(BigDecimal.ZERO);
            balance.setOpen(BigDecimal.ZERO);
            balance.setCurrency(c);
            balance.setAccount(saved);
            balance.setBaseBalance(true);
            balanceService.updateBalance(balance);
        });

        //TODO enable after restart
        //mailService.sendRegistrationMail(account);
        login(saved);
        return saved;
    }

    @Override
    public AccountDTO getCurrentAccountDTO() {
        Account account = getCurrentAccount();
        if(account==null) {
            return null;
        }

        AccountDTO accountDTO = getAccountDTO(account);

        List<Balance> balances = balanceService.getBaseBalancesByAccount(account);
        accountDTO.setBalance(balances);

        return accountDTO;
    }

    private AccountDTO getAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setMail(account.getMail());
        accountDTO.setReferral(account.getReferral());
        accountDTO.setReferredCount(account.getReferredCount());
        return accountDTO;
    }

    @Override
    public Account buildAccount(String mail, String pwd, String phone, String curr) throws TradeException {
        Account anotherAccount = accountRepository.findByMail(mail);
        if (anotherAccount != null) {
            throw new TradeException("Can't create account with this mail!");
        }

        pwd = passwordEncoder.encode(pwd);
        Account account = new Account(mail, pwd);
        account.setPhone(phone);
        account.setGuid(UUID.randomUUID().toString());
        account.setRole(Account.F_ROLE_ACCOUNT);

        return account;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account create(Account account) {
        account = accountRepository.save(account);
        balanceService.getBalance(Currency.USD, account);
        return account;
    }

    @Override
    public Account findByMail(String login) {
        return accountRepository.findByMail(login);
    }

    @Override
    public Account login(Account account) {
        Authentication auth =
                new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return account;
    }
}
