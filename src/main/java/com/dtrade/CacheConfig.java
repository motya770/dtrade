package com.dtrade;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

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
