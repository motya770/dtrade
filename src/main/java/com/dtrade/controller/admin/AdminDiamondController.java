package com.dtrade.controller.admin;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondType;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private IDiamondService diamondService;

    @Autowired
    private IStockService stockService;

    @RequestMapping(value = "/make-ipo/{id}", method = RequestMethod.GET)
    public String makeIPO(@PathVariable Long id, Model model) {

        stockService.makeIPO(id);

        return "redirect:/admin/diamond/list";
    }

    @RequestMapping(value = "/new-entity", method = RequestMethod.GET)
    public String newEntity(@ModelAttribute Diamond diamond, Model model) {
        model.addAttribute("diamondTypes", Stream.of(DiamondType.values()).collect(Collectors.toMap(DiamondType::name, DiamondType::name)));
        return "admin/diamond/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id")  Long diamondId, Model model) {

        model.addAttribute("diamondTypes", Stream.of(DiamondType.values()).collect(Collectors.toMap(DiamondType::name, DiamondType::name)));
        model.addAttribute("diamond",  diamondService.find(diamondId));

        return "admin/diamond/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Diamond diamond, Model model) {

        if(diamond.getId()==null) {
            diamondService.create(diamond);
        }else{
            diamondService.update(diamond);
        }

        //model.addAttribute(saved);
        return "redirect:/admin/diamond/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Diamond> diamonds = diamondService.getAllDiamonds().getContent();
        model.addAttribute("diamonds", diamonds);
        System.out.println("d: " + diamonds.size());
        return "admin/diamond/list";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        ///List<Diamond> diamonds = diamondRepository.findAll();
        return "test";
    }

}
