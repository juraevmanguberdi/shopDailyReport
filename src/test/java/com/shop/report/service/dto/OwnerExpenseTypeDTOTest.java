package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OwnerExpenseTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerExpenseTypeDTO.class);
        OwnerExpenseTypeDTO ownerExpenseTypeDTO1 = new OwnerExpenseTypeDTO();
        ownerExpenseTypeDTO1.setId(1L);
        OwnerExpenseTypeDTO ownerExpenseTypeDTO2 = new OwnerExpenseTypeDTO();
        assertThat(ownerExpenseTypeDTO1).isNotEqualTo(ownerExpenseTypeDTO2);
        ownerExpenseTypeDTO2.setId(ownerExpenseTypeDTO1.getId());
        assertThat(ownerExpenseTypeDTO1).isEqualTo(ownerExpenseTypeDTO2);
        ownerExpenseTypeDTO2.setId(2L);
        assertThat(ownerExpenseTypeDTO1).isNotEqualTo(ownerExpenseTypeDTO2);
        ownerExpenseTypeDTO1.setId(null);
        assertThat(ownerExpenseTypeDTO1).isNotEqualTo(ownerExpenseTypeDTO2);
    }
}
