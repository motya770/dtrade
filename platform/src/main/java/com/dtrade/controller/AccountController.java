package com.dtrade.controller;

import com.dtrade.exception.TradeException;
import com.dtrade.model.account.Account;
import com.dtrade.model.account.AccountDTO;
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


    @RequestMapping(value = "/demo-login", method = RequestMethod.GET)
    public String demoLogin(@RequestParam String ref)

    {
        accountService.loginByRef(ref);
        return "redirect:/trade#!/basic";
    }

    @CrossOrigin
    @RequestMapping(value = "/create-referral", method = RequestMethod.POST)
    public @ResponseBody AccountDTO signToReferral(@RequestParam String mail,
                                 @RequestParam(value = "hidden_ref", required = false) String ref)
    {
        return accountService.createReferalAccount(mail, ref);
        //return "redirect:/referral?myRef=" +
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Account register(@RequestBody RegistrationAccount account,
                            HttpServletRequest request
    ) throws Exception {
        //TODO save mail phone country

       validateCredentials(account.getEmail(), account.getPwd(), null);// account.getPhone());

       String pwd = org.apache.commons.text.StringEscapeUtils.escapeJava(account.getPwd());
       String email = org.apache.commons.text.StringEscapeUtils.escapeJava(account.getEmail());
       //String phone = org.apache.commons.text.StringEscapeUtils.escapeJava(account.getPhone());

       //TODO return this thing
       //turingService.check(account.getCaptcha(), request.getRemoteAddr());
       return accountService.createRealAccount(email, pwd, null, null);
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

        /*
        if (phone.length() < 6) {
            throw new TradeException("Phone should be at least 6 signs.");
        }*/
    }

    @RequestMapping(value = "/get-current", method = RequestMethod.POST)
    @ResponseBody
    public Object getCurrentAccount() throws IOException {
         AccountDTO accountDTO = accountService.getCurrentAccountDTO();
        if(accountDTO!=null){
            return accountDTO;
        }
        return mapper.readTree("{\"account\" : \"empty\"}");
    }
}
