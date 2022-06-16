package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OwnerExpenseMapperTest {

    private OwnerExpenseMapper ownerExpenseMapper;

    @BeforeEach
    public void setUp() {
        ownerExpenseMapper = new OwnerExpenseMapperImpl();
    }
}
