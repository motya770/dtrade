package com.dtrade;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.image.Image;
import com.dtrade.service.IImageService;
import com.dtrade.service.impl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Transactional
public class ImageServiceTest extends BaseTest {

    @Autowired
    private IImageService imageService;

}
