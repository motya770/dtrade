package com.dtrade.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class ConsulUtils {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Cacheable(value = "engineUrl")
    public String engineUrl() {
        return discoveryClient.getInstances("engine")
                .stream()
                .map(si -> si.getUri())
                .findFirst().get().toString();
    }
}
