package com.dtrade;


import com.dtrade.model.config.AssetType;
import com.dtrade.model.config.Config;
import com.dtrade.service.IConfigService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ConfigServiceTest {

    @Autowired
    private IConfigService configService;

    @Test
    public void findByAssetType(){
        Config config = configService.findByAssetType(AssetType.CRYPTO);
        Assert.assertNotNull(config);
        Assert.assertTrue(config.getAssetName().toLowerCase().contains("coin"));
    }

    @Test
    public void getActiveConfig(){

        Config config = configService.getActiveConfig();
        Assert.assertNotNull(config);
        Assert.assertTrue(config.getAssetName().toLowerCase().contains("coin"));
    }

    @Test
    public void find(){
        Config config = configService.findByAssetType(AssetType.WINE);
        Config reread = configService.find(config.getId());
        Assert.assertTrue(config.equals(reread));
    }

    @Test
    public void update(){

        Config config  = configService.getActiveConfig();
        String nameSaved =  config.getAssetName();
        config.setAssetName("test1");

        config =  configService.update(config);


        Assert.assertTrue(config.getAssetName().equals("test1"));
        Assert.assertTrue(!config.getAssetName().equals(nameSaved));

    }

    @Test
    public void create(){

        Config config = new Config();
        config.setAssetName("test32");
        config.setAssetNameForListing("Test23");
        config.setAssetType(AssetType.FLATS);
        config.setActive(false);
        config =  configService.create(config);

        Assert.assertNotNull(config);
        Assert.assertTrue(config.getAssetName().equals("test32"));
    }

    @Test
    public void findAll(){

        List<Config> configs = configService.findAll();

       long counter = configs.stream().filter(c -> c.getAssetType().equals(AssetType.CRYPTO)
                || c.getAssetType().equals(AssetType.WINE)
                || c.getAssetType().equals(AssetType.DIAMOND)
        ).count();
       Assert.assertEquals(3, counter);
    }
}
