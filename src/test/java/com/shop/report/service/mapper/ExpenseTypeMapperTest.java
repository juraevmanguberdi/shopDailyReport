package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseTypeMapperTest {

    private ExpenseTypeMapper expenseTypeMapper;

    @BeforeEach
    public void setUp() {
        expenseTypeMapper = new ExpenseTypeMapperImpl();
    }
}
