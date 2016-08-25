package com.dtrade.controller.admin;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.repository.diamond.DiamondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by kudelin on 8/24/16.
 */
@Controller
@RequestMapping(value = "/admin/diamond")
public class AdminDiamondController {

    @Autowired
    private DiamondRepository diamondRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model){
        Diamond diamond = new Diamond();
        model.addAttribute(diamond);
        return "admin/diamond/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Diamond> diamonds = diamondRepository.findAll();
        return "admin/diamond/list";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model){
        ///List<Diamond> diamonds = diamondRepository.findAll();
        return "test";
    }


}
