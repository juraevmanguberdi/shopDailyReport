package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BorrowedDebtTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BorrowedDebtTypeDTO.class);
        BorrowedDebtTypeDTO borrowedDebtTypeDTO1 = new BorrowedDebtTypeDTO();
        borrowedDebtTypeDTO1.setId(1L);
        BorrowedDebtTypeDTO borrowedDebtTypeDTO2 = new BorrowedDebtTypeDTO();
        assertThat(borrowedDebtTypeDTO1).isNotEqualTo(borrowedDebtTypeDTO2);
        borrowedDebtTypeDTO2.setId(borrowedDebtTypeDTO1.getId());
        assertThat(borrowedDebtTypeDTO1).isEqualTo(borrowedDebtTypeDTO2);
        borrowedDebtTypeDTO2.setId(2L);
        assertThat(borrowedDebtTypeDTO1).isNotEqualTo(borrowedDebtTypeDTO2);
        borrowedDebtTypeDTO1.setId(null);
        assertThat(borrowedDebtTypeDTO1).isNotEqualTo(borrowedDebtTypeDTO2);
    }
}
