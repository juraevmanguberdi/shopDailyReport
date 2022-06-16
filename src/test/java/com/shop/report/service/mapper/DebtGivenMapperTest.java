package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DebtGivenMapperTest {

    private DebtGivenMapper debtGivenMapper;

    @BeforeEach
    public void setUp() {
        debtGivenMapper = new DebtGivenMapperImpl();
    }
}
