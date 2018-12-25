package com.dtrade.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapAttributeConfig;
import com.hazelcast.config.MapIndexConfig;
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
        MapAttributeConfig attributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());


        Config config = new Config();
        config.setProperty("hazelcast.partition.count", "2");


        config.getMapConfig(HazelcastSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .addMapAttributeConfig(attributeConfig)
                .addMapIndexConfig(new MapIndexConfig(
                        HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));
        return Hazelcast.newHazelcastInstance(config);
    }

    @Profile("prod")
    @Bean
    public HazelcastInstance hazelcastInstanceProd() {
        MapAttributeConfig attributeConfig = new MapAttributeConfig()
                .setName(HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE)
                .setExtractor(PrincipalNameExtractor.class.getName());

        Config config = new Config();
        config.setProperty("hazelcast.partition.count", "2");

        config.getMapConfig(HazelcastSessionRepository.DEFAULT_SESSION_MAP_NAME)
                .addMapAttributeConfig(attributeConfig)
                .addMapIndexConfig(new MapIndexConfig(
                        HazelcastSessionRepository.PRINCIPAL_NAME_ATTRIBUTE, false));

        JoinConfig join = config.getNetworkConfig().getJoin();
        join.getMulticastConfig().setEnabled(false);
        join.getAwsConfig().setEnabled(false);
        join.getTcpIpConfig().setEnabled(true).setMembers(
                Arrays.asList("engine-01"));

        return Hazelcast.newHazelcastInstance(config);
    }
}

