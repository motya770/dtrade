package com.dtrade.model.config;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Config {

    @Id
    @GeneratedValue
    private Long id;

    private AssetType assetType;

    private String assetName;

    private String assetNameForListing;

    private boolean active;

}
