package com.dtrade.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class ConsulUtils {

//    @Autowired
//    private DiscoveryClient discoveryClient;


    @Cacheable(value = "engineUrl")
    public String engineUrl() {
        return "http://localhost:8081";
//        return discoveryClient.getInstances("engine")
//                .stream()
//                .map(si -> si.getUri())
//                .findFirst().get().toString();
    }
}
