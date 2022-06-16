package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OwnerExpenseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerExpenseDTO.class);
        OwnerExpenseDTO ownerExpenseDTO1 = new OwnerExpenseDTO();
        ownerExpenseDTO1.setId(1L);
        OwnerExpenseDTO ownerExpenseDTO2 = new OwnerExpenseDTO();
        assertThat(ownerExpenseDTO1).isNotEqualTo(ownerExpenseDTO2);
        ownerExpenseDTO2.setId(ownerExpenseDTO1.getId());
        assertThat(ownerExpenseDTO1).isEqualTo(ownerExpenseDTO2);
        ownerExpenseDTO2.setId(2L);
        assertThat(ownerExpenseDTO1).isNotEqualTo(ownerExpenseDTO2);
        ownerExpenseDTO1.setId(null);
        assertThat(ownerExpenseDTO1).isNotEqualTo(ownerExpenseDTO2);
    }
}
