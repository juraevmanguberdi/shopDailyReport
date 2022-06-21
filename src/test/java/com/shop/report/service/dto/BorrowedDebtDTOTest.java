package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BorrowedDebtDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorrowedDebtDTO.class);
        BorrowedDebtDTO borrowedDebtDTO1 = new BorrowedDebtDTO();
        borrowedDebtDTO1.setId(1L);
        BorrowedDebtDTO borrowedDebtDTO2 = new BorrowedDebtDTO();
        assertThat(borrowedDebtDTO1).isNotEqualTo(borrowedDebtDTO2);
        borrowedDebtDTO2.setId(borrowedDebtDTO1.getId());
        assertThat(borrowedDebtDTO1).isEqualTo(borrowedDebtDTO2);
        borrowedDebtDTO2.setId(2L);
        assertThat(borrowedDebtDTO1).isNotEqualTo(borrowedDebtDTO2);
        borrowedDebtDTO1.setId(null);
        assertThat(borrowedDebtDTO1).isNotEqualTo(borrowedDebtDTO2);
    }
}
