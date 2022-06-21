package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BorrowedDebtTypeMapperTest {

    private BorrowedDebtTypeMapper borrowedDebtTypeMapper;

    @BeforeEach
    public void setUp() {
        borrowedDebtTypeMapper = new BorrowedDebtTypeMapperImpl();
    }
}
