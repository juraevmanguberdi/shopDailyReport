package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequiredProductMapperTest {

    private RequiredProductMapper requiredProductMapper;

    @BeforeEach
    public void setUp() {
        requiredProductMapper = new RequiredProductMapperImpl();
    }
}
