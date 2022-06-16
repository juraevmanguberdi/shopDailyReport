package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DebtGivenDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebtGivenDTO.class);
        DebtGivenDTO debtGivenDTO1 = new DebtGivenDTO();
        debtGivenDTO1.setId(1L);
        DebtGivenDTO debtGivenDTO2 = new DebtGivenDTO();
        assertThat(debtGivenDTO1).isNotEqualTo(debtGivenDTO2);
        debtGivenDTO2.setId(debtGivenDTO1.getId());
        assertThat(debtGivenDTO1).isEqualTo(debtGivenDTO2);
        debtGivenDTO2.setId(2L);
        assertThat(debtGivenDTO1).isNotEqualTo(debtGivenDTO2);
        debtGivenDTO1.setId(null);
        assertThat(debtGivenDTO1).isNotEqualTo(debtGivenDTO2);
    }
}
