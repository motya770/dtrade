package com.dtrade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goptions.bp.exceptions.GOptionsException;
import com.goptions.bp.model.account.Account;
import com.goptions.bp.model.currency.Currency;
import com.goptions.bp.service.IAccountService;
import com.goptions.bp.service.ITuringService;
import com.goptions.bp.utils.UtilsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@RestController
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
    public Account register(@RequestParam String login,
                            @RequestParam String pwd,
                            @RequestParam String phone,
                            @RequestParam(value = "g-recaptcha-response") String recaptchaResponse,
                            @RequestParam String currency,
                            HttpServletRequest request
    ) throws Exception {
        //TODO save mail phone country
        validateCredentials(login, pwd, phone, currency);

        turingService.check(recaptchaResponse, request.getRemoteAddr());

        return accountService.createRealAccount(login, pwd, phone, currency);
    }

    @RequestMapping(value = "/confirm-registration", method = RequestMethod.POST)
    @ResponseBody
    public Account confirmRegistration(@RequestParam String guid) throws GOptionsException {
        if(StringUtils.isEmpty(guid)){
            throw new GOptionsException("Guid can't be empty");
        }

        return accountService.confirmRegistration(guid);
    }

    @RequestMapping(value = "/cancel-registration", method = RequestMethod.POST)
    @ResponseBody
    public Account cancelRegistration(@RequestParam String guid) throws GOptionsException {
        if(StringUtils.isEmpty(guid)){
            throw new GOptionsException("Guid can't be empty");
        }

        return accountService.cancelRegistration(guid);
    }

    @ResponseBody
    public Account updateAccount() {
        //TODO add update account functionality
        return null;
    }

    @RequestMapping(value = "/create-demo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createDemoAccount(@RequestParam String login,
                                                 @RequestParam String pwd,
                                                 @RequestParam String phone,
                                                 @RequestParam String currency,
                                                 @RequestParam(value = "g-recaptcha-response") String recaptchaResponse,
                                                 HttpServletRequest request
    ) throws Exception {

        //TODO save mail phone country
        validateCredentials(login, pwd, phone, currency);

        turingService.check(recaptchaResponse, request.getRemoteAddr());

        Account account = accountService.createDemoAccount(login, pwd, phone, currency);

        Map<String, Object> response = new HashMap<>();
        response.put("account", account);
        response.put("redirect", "/platform");

        return response;
    }

    public void validateCredentials(String login, String pwd, String phone, String currency) throws GOptionsException {
        if (login.length() < 6) {
            throw new GOptionsException("Login should be 6 numbers at least");
        }

        //login and email are the same thing
        if (!UtilsHelper.isValidEmailAddress(login)) {
            throw new GOptionsException("Email is not valid!");
        }

        if (pwd.length() < 8) {
            throw new GOptionsException("Password should be 8 numbers at least");
        }

        if (phone.length() < 6) {
            throw new GOptionsException("Phone should be at least 6 signs.");
        }

        Currency.valueOf(currency);
    }

    @RequestMapping(value = "/get-current", method = RequestMethod.POST)
    @ResponseBody
    public Object getCurrentAccount() throws IOException {
        if (accountService.getCurrentAccount() != null) {
            Account account = accountService.getCurrentAccount().getAccount();
            if(account!=null) {
                return accountService.read(account.getId());
            }
        }
        return mapper.readTree("{\"account\" : \"empty\"}");
    }
}
