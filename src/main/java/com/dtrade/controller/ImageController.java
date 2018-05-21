package com.dtrade.controller;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.image.Image;
import com.dtrade.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/image/", method = RequestMethod.POST)
@RestController
public class ImageController {

    @Autowired
    private IImageService imageService;

    @PostMapping(value = "/diamond-list")
    public List<String> getDiamondImagesUrl(@RequestParam Diamond diamond){
        return imageService.getDiamondImagesUrl(diamond);
    }

    @PostMapping(value = "/diamond-image")
    public ResponseEntity<?> getDiamondImage(@RequestParam Diamond diamond, @RequestParam Image image){
        image = imageService.getDiamondImage(diamond, image);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(image.getType()))
                .contentLength(image.getPic().length)
                .body(image.getPic());
    }
}
