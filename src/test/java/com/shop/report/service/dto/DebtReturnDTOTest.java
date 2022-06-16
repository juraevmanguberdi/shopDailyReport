package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DebtReturnDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebtReturnDTO.class);
        DebtReturnDTO debtReturnDTO1 = new DebtReturnDTO();
        debtReturnDTO1.setId(1L);
        DebtReturnDTO debtReturnDTO2 = new DebtReturnDTO();
        assertThat(debtReturnDTO1).isNotEqualTo(debtReturnDTO2);
        debtReturnDTO2.setId(debtReturnDTO1.getId());
        assertThat(debtReturnDTO1).isEqualTo(debtReturnDTO2);
        debtReturnDTO2.setId(2L);
        assertThat(debtReturnDTO1).isNotEqualTo(debtReturnDTO2);
        debtReturnDTO1.setId(null);
        assertThat(debtReturnDTO1).isNotEqualTo(debtReturnDTO2);
    }
}
