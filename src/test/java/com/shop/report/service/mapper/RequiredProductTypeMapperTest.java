package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequiredProductTypeMapperTest {

    private RequiredProductTypeMapper requiredProductTypeMapper;

    @BeforeEach
    public void setUp() {
        requiredProductTypeMapper = new RequiredProductTypeMapperImpl();
    }
}
