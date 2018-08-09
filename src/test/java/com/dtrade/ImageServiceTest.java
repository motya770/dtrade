package com.dtrade;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.image.Image;
import com.dtrade.service.IImageService;
import com.dtrade.service.impl.ImageService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Transactional
public class ImageServiceTest extends BaseTest {

    @Autowired
    private IImageService imageService;

}
