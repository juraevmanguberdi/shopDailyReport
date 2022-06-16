package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LiabilityRegistryMapperTest {

    private LiabilityRegistryMapper liabilityRegistryMapper;

    @BeforeEach
    public void setUp() {
        liabilityRegistryMapper = new LiabilityRegistryMapperImpl();
    }
}
