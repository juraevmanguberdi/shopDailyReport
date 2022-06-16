package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OwnerExpenseTypeMapperTest {

    private OwnerExpenseTypeMapper ownerExpenseTypeMapper;

    @BeforeEach
    public void setUp() {
        ownerExpenseTypeMapper = new OwnerExpenseTypeMapperImpl();
    }
}
