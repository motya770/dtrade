package com.dtrade.service;

import com.dtrade.model.config.Config;

import java.util.List;

public interface IConfigService {

    Config getActiveConfig();

    Config find(Long configId);

    Config update(Config config);

    Config create(Config config);

    List<Config> findAll();
}
