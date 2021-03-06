package com.dtrade.service;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.image.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image createImage(Diamond diamond, MultipartFile multipartFile);

    Image getDiamondImage(Image image);

    List<String> getDiamondImagesUrl(Diamond diamond);

    Image removeImage(Diamond diamond, Image image);
}
