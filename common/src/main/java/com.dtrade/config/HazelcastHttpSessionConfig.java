package com.dtrade.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.hazelcast.HazelcastSessionRepository;
import org.springframework.session.hazelcast.PrincipalNameExtractor;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import java.util.Arrays;


@EnableHazelcastHttpSession
@Configuration
public class HazelcastHttpSessionConfig {

    @Profile("dev")
    @Bean
    public HazelcastInstance hazelcastInstanceDev() {

        Config config =  createConfig();
        return Hazelcast.newHazelcastInstance(config);
    }

    private Config createConfig(){
        MapAttributeConfig attributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());


        Config config = new Config();
        config.setProperty("hazelcast.partition.count", "6");

        MaxSizeConfig maxSizeConfig = new MaxSizeConfig();
        maxSizeConfig.setMaxSizePolicy(MaxSizeConfig.MaxSizePolicy.USED_HEAP_PERCENTAGE);
        maxSizeConfig.setSize(10);

        config.getMapConfig(HazelcastSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .addMapAttributeConfig(attributeConfig)
                .addMapIndexConfig(new MapIndexConfig(
                        HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false))
                .setEvictionPolicy(EvictionPolicy.LRU)
                //.setMaxIdleSeconds(3 * 60 * 60)
                .setMaxSizeConfig(maxSizeConfig);

        return config;

    }

    @Profile("prod")
    @Bean
    public HazelcastInstance hazelcastInstanceProd() {
        Config config = createConfig();

        JoinConfig join = config.getNetworkConfig().getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getAwsConfig().setEnabled(false);
        join.getTcpIpConfig().setEnabled(true).setMembers(
                Arrays.asList("engine-01"));

        return Hazelcast.newHazelcastInstance(config);
    }
}

