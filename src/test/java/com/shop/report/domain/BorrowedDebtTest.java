package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BorrowedDebtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorrowedDebt.class);
        BorrowedDebt borrowedDebt1 = new BorrowedDebt();
        borrowedDebt1.setId(1L);
        BorrowedDebt borrowedDebt2 = new BorrowedDebt();
        borrowedDebt2.setId(borrowedDebt1.getId());
        assertThat(borrowedDebt1).isEqualTo(borrowedDebt2);
        borrowedDebt2.setId(2L);
        assertThat(borrowedDebt1).isNotEqualTo(borrowedDebt2);
        borrowedDebt1.setId(null);
        assertThat(borrowedDebt1).isNotEqualTo(borrowedDebt2);
    }
}
