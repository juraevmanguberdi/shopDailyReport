package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetRegistryMapperTest {

    private AssetRegistryMapper assetRegistryMapper;

    @BeforeEach
    public void setUp() {
        assetRegistryMapper = new AssetRegistryMapperImpl();
    }
}
