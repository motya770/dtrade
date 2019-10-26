package com.dtrade.model.config;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Config {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    private AssetType assetType;

    private String assetName;

    private String assetNameForListing;

    private boolean active;

}
