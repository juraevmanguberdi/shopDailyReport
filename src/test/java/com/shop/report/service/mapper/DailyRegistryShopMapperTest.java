package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DailyRegistryShopMapperTest {

    private DailyRegistryShopMapper dailyRegistryShopMapper;

    @BeforeEach
    public void setUp() {
        dailyRegistryShopMapper = new DailyRegistryShopMapperImpl();
    }
}
