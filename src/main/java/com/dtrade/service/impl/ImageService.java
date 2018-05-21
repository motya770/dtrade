package com.dtrade.service.impl;

import com.dtrade.exception.TradeException;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.image.Image;
import com.dtrade.repository.image.ImageRepository;
import com.dtrade.service.IDiamondService;
import com.dtrade.service.IImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService implements IImageService {

    private static final Logger logger = LoggerFactory.getLogger(TradeOrderService.class);

    @Autowired
    private IDiamondService diamondService;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image createImage(Diamond diamond, MultipartFile multipartFile) {

        diamond = diamondService.find(diamond.getId());
        byte[] bytes;
        try {
            bytes = multipartFile.getBytes();
        }catch (Exception e){
            logger.error("Can't upload image", e);
            throw new TradeException("Can't upload image", e);
        }
        String name =  multipartFile.getName();
        String imageType = multipartFile.getContentType();

        Image image = new Image();
        image.setDiamond(diamond);
        image.setName(name);
        image.setPic(bytes);
        image.setType(imageType);
        image = imageRepository.save(image);
        return image;
    }

    @Override
    public Image getDiamondImage(Diamond diamond, Image image) {
        diamond =diamondService.find(diamond.getId());
        if(diamond == null){
            throw new TradeException("Diamond is null");
        }

        image = imageRepository.findOne(image.getId());
        if(image == null){
            throw new TradeException("Image is null");
        }

        if(!diamond.getId().equals(image.getDiamond().getId())){
            throw new TradeException("Image doesn't belong to the diamond");
        }

        return image;
    }

    @Override
    public List<String> getDiamondImagesUrl(Diamond diamond) {
        return diamond.getImages().stream().map(i ->
             new String("/images/" + i.getId())).collect(Collectors.toList());
    }
}
