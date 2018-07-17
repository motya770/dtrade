package com.dtrade.controller.admin;


import com.dtrade.model.config.AssetType;
import com.dtrade.model.config.Config;
import com.dtrade.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/admin/config")
public class AdminConfigController {

    @Autowired
    private IConfigService configService;

    @RequestMapping(value = "/new-entity", method = RequestMethod.GET)
    public String newEntity(@ModelAttribute Config config, Model model) {

        model = addAttributes(model);

        return "admin/config/edit";
    }

    private  Model addAttributes(Model model){
       model.addAttribute("assetTypes", Stream.of(AssetType.values()).collect(Collectors.toMap(AssetType::name, AssetType::name)));
       return model;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id")  Long configId, Model model) {

        model = addAttributes(model);
        model.addAttribute("config",  configService.find(configId));

        return "admin/config/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Config config, Model model) {

        if(config.getId()==null) {
            configService.create(config);
        }else{
            configService.update(config);
        }
        return "redirect:/admin/config/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Config> configs = configService.findAll();
        model.addAttribute("configs", configs);
        return "admin/config/list";
    }
}
