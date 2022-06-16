package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LiabilityLiveMapperTest {

    private LiabilityLiveMapper liabilityLiveMapper;

    @BeforeEach
    public void setUp() {
        liabilityLiveMapper = new LiabilityLiveMapperImpl();
    }
}
