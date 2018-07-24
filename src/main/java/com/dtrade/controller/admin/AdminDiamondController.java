package com.dtrade.controller.admin;

import com.dtrade.model.currency.Currency;
import com.dtrade.model.diamond.*;
import com.dtrade.model.image.Image;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IImageService;
import com.dtrade.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private IImageService imageService;

    @PostMapping("/image-remove")
    public @ResponseBody
    ResponseEntity<?> removeImage(@RequestParam("diamond") Diamond diamond, @RequestParam("image") Image image) {
        try{
            image = imageService.removeImage(diamond, image);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Removed image : <br/>" + image.getId(), HttpStatus.OK);
    }

    @PostMapping("/image-upload")
    public @ResponseBody
    ResponseEntity<?> handleFileUpload(@RequestParam("diamond") Diamond diamond,
                                       @RequestParam("file") MultipartFile file) {
        Image image;
        try{
            image = imageService.createImage(diamond, file);
        }
        catch (Exception e){
          return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Uploaded to: <br/>" + image.getId(), HttpStatus.OK);
    }

    @RequestMapping(value = "/make-ipo/{diamondId}", method = RequestMethod.POST)
    public String makeIPO(@PathVariable Long diamondId, Model model) {

        stockService.makeIPO(diamondId);

        return "redirect:/admin/diamond/list";
    }

    @RequestMapping(value = "/new-entity", method = RequestMethod.GET)
    public String newEntity(@ModelAttribute Diamond diamond, Model model) {

        model = addAttributes(model);

        return "admin/diamond/edit";
    }

    private  Model addAttributes(Model model){
        model.addAttribute("diamondTypes", Stream.of(DiamondType.values()).collect(Collectors.toMap(DiamondType::name, DiamondType::name)));
        model.addAttribute("colors", Stream.of(Color.values()).collect(Collectors.toMap(Color::name, Color::name)));
        model.addAttribute("cuts", Stream.of(Cut.values()).collect(Collectors.toMap(Cut::name, Cut::name)));
        model.addAttribute("clarities", Stream.of(Clarity.values()).collect(Collectors.toMap(Clarity::name, Clarity::name)));
        model.addAttribute("currencies", Stream.of(Currency.values()).collect(Collectors.toMap(Currency::name, Currency::name)));
        return model;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam(value = "id")  Long diamondId, Model model) {

        model = addAttributes(model);
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
