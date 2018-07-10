package com.dtrade.repository.config;

import com.dtrade.model.config.AssetType;
import com.dtrade.model.config.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ConfigRepository extends JpaRepository<Config, Long> {

    Config findByAssetType(AssetType assetType);

    Config findByActive(Boolean active);
}
