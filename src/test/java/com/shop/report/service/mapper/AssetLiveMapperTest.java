package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssetLiveMapperTest {

    private AssetLiveMapper assetLiveMapper;

    @BeforeEach
    public void setUp() {
        assetLiveMapper = new AssetLiveMapperImpl();
    }
}
