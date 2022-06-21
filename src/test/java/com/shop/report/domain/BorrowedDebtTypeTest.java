package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BorrowedDebtTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorrowedDebtType.class);
        BorrowedDebtType borrowedDebtType1 = new BorrowedDebtType();
        borrowedDebtType1.setId(1L);
        BorrowedDebtType borrowedDebtType2 = new BorrowedDebtType();
        borrowedDebtType2.setId(borrowedDebtType1.getId());
        assertThat(borrowedDebtType1).isEqualTo(borrowedDebtType2);
        borrowedDebtType2.setId(2L);
        assertThat(borrowedDebtType1).isNotEqualTo(borrowedDebtType2);
        borrowedDebtType1.setId(null);
        assertThat(borrowedDebtType1).isNotEqualTo(borrowedDebtType2);
    }
}
