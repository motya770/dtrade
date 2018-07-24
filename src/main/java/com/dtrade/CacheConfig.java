package com.dtrade;

import com.dtrade.model.tradeorder.TradeOrder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig extends  CachingConfigurerSupport {


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
