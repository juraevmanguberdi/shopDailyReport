package com.shop.report.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BorrowedDebtMapperTest {

    private BorrowedDebtMapper borrowedDebtMapper;

    @BeforeEach
    public void setUp() {
        borrowedDebtMapper = new BorrowedDebtMapperImpl();
    }
}
