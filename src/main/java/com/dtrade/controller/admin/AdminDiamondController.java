package com.dtrade.controller.admin;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.diamond.DiamondType;
import com.dtrade.repository.diamond.DiamondRepository;
import com.dtrade.service.IDiamondService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kudelin on 8/24/16.
 */
@Controller
@RequestMapping(value = "/admin/diamond")
public class AdminDiamondController {

    @Autowired
    private DiamondRepository diamondRepository;

    @Autowired
    private IDiamondService diamondService;

    @RequestMapping(value = "/new-entity", method = RequestMethod.GET)
    public String newEntity(@ModelAttribute Diamond diamond, Model model){
        model.addAttribute("diamondTypes", Stream.of(DiamondType.values()).collect(Collectors.toMap(DiamondType::name, DiamondType::name)));
        return "admin/diamond/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Diamond diamond, Model model){
        //Diamond diamond = new Diamond();
        Diamond saved = diamondService.create(diamond);
        model.addAttribute(saved);
        return "redirect:/admin/diamond/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Diamond> diamonds = diamondRepository.findAll();
        model.addAttribute("diamonds", diamonds);
        System.out.println("d: " + diamonds.size());
        return "admin/diamond/list";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model){
        ///List<Diamond> diamonds = diamondRepository.findAll();
        return "test";
    }

}
