package com.dtrade.controller.admin;

import com.dtrade.model.account.Account;
import com.dtrade.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by kudelin on 8/28/16.
 */
@Controller
@RequestMapping(value = "/admin/account")
public class AdminAccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> list() {
        return accountService.findAll();
    }

    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    public String disable(Model model, Long accountId) {
        Account account = accountService.disable(accountId);
        model.addAttribute(account);
        return "/admin/account/show";
    }

    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public String enable(Model model, Long accountId) {
        Account account = accountService.enable(accountId);
        model.addAttribute(account);
        return "/admin/account/show";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public Account show(Long accountId) {
        return accountService.find(accountId);
    }
}
