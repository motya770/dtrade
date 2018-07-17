package com.dtrade.service.impl;

import com.dtrade.model.config.AssetType;
import com.dtrade.model.config.Config;
import com.dtrade.repository.config.ConfigRepository;
import com.dtrade.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigService implements IConfigService {

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public Config findByAssetType(AssetType assetType) {
        return configRepository.findByAssetType(assetType);
    }

    @Override
    public Config getActiveConfig() {
        return configRepository.findByActive(true);
    }

    @Override
    public Config create(Config config) {
        return configRepository.save(config);
    }

    @Override
    public Config update(Config config) {
        return configRepository.save(config);
    }

    @Override
    public Config find(Long configId) {
        return configRepository.findOne(configId);
    }

    @Override
    public List<Config> findAll() {
        return configRepository.findAll();
    }
}
