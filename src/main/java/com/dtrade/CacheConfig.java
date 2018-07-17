package com.dtrade;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig  {

    /*
    @Override
    @Bean
    public CacheManager cacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        return cacheManager;
    }

    @Bean
    public CacheManager timeoutCacheManager() {

       // return new ConcurrentMapCacheManager("getHistoryTradeOrders");
        EhCacheCacheManager cacheCacheManager = new EhCacheCacheManager();
        net.sf.ehcache.config.Configuration configuration = new net.sf.ehcache.config.Configuration();
        cacheCacheManager.setCacheManager(net.sf.ehcache.CacheManager.create(configuration.name("getHistoryTradeOrders")));
        return cacheCacheManager;
    }*/

       /*
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(20000)
                .expireAfterWrite(20, TimeUnit.MILLISECONDS);
        cacheManager.setCacheBuilder(cacheBuilder);*/
    //return cacheManager;
}
