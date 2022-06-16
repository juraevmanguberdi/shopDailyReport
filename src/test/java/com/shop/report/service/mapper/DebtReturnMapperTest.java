package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DebtReturnMapperTest {

    private DebtReturnMapper debtReturnMapper;

    @BeforeEach
    public void setUp() {
        debtReturnMapper = new DebtReturnMapperImpl();
    }
}
