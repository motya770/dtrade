package com.dtrade;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig  {

    /*
    @Bean
    Config config() {
        Config c = new Config();
        c.setInstanceName("cache-1");
        c.getGroupConfig().setName("test").setPassword("tesla1tesla1");
        ManagementCenterConfig mcc = new ManagementCenterConfig().setUrl("http://127.0.0.1:8080/hazelcast-mancenter").setEnabled(true);
        c.setManagementCenterConfig(mcc);
        //SerializerConfig sc = new SerializerConfig().setTypeClass(Quote.class);
        //c.getSerializationConfig().addSerializerConfig(sc);
        return c;
    }*/

    /*
    @Bean
    public CacheManager cacheManager() {

        Cache<Object, Object> cache =
                Caffeine.newBuilder()
                        .maximumSize(10_000)
                        .expireAfterWrite(50, TimeUnit.MILLISECONDS)
                        .build();

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new CaffeineCache("getHistoryTradeOrders", cache)));

        return cacheManager;
    }*/
       /*
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(20000)
                .expireAfterWrite(20, TimeUnit.MILLISECONDS);
        cacheManager.setCacheBuilder(cacheBuilder);*/
       //return cacheManager;
}
