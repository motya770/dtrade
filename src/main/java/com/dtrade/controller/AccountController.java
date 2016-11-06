package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.account.RegistrationAccount;
import com.dtrade.service.IAccountService;
import com.dtrade.service.ITuringService;
import com.dtrade.utils.UtilsHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value = "/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ITuringService turingService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Account register(@RequestBody RegistrationAccount account,
                            HttpServletRequest request
    ) throws Exception {
        //TODO save mail phone country
        validateCredentials(account.getEmail(), account.getPwd(), account.getPhone());


        //TODO return this thing
        //turingService.check(account.getCaptcha(), request.getRemoteAddr());

        return accountService.createRealAccount(account.getEmail(), account.getPwd(), account.getPhone(), null);
    }

    @RequestMapping(value = "/confirm-registration", method = RequestMethod.POST)
    @ResponseBody
    public Account confirmRegistration(@RequestParam String guid) throws TradeException {
        if (StringUtils.isEmpty(guid)) {
            throw new TradeException("Guid can't be empty");
        }

        return accountService.confirmRegistration(guid);
    }

    @RequestMapping(value = "/cancel-registration", method = RequestMethod.POST)
    @ResponseBody
    public Account cancelRegistration(@RequestParam String guid) throws TradeException {
        if (StringUtils.isEmpty(guid)) {
            throw new TradeException("Guid can't be empty");
        }

        return accountService.cancelRegistration(guid);
    }


    public void validateCredentials(String email, String pwd, String phone) throws TradeException {

        if (!UtilsHelper.isValidEmailAddress(email)) {
            throw new TradeException("Email is not valid!");
        }

        if (pwd.length() < 8) {
            throw new TradeException("Password should be 8 numbers at least");
        }

        if (phone.length() < 6) {
            throw new TradeException("Phone should be at least 6 signs.");
        }

    }

    @RequestMapping(value = "/get-current", method = RequestMethod.POST)
    @ResponseBody
    public Object getCurrentAccount() throws IOException {
        if (accountService.getCurrentAccount() != null) {
            Account account = accountService.getCurrentAccount();
            if (account != null) {
                return accountService.find(account.getId());
            }
        }
        return mapper.readTree("{\"account\" : \"empty\"}");
    }
}
