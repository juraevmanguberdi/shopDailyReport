package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequiredProductTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequiredProductTypeDTO.class);
        RequiredProductTypeDTO requiredProductTypeDTO1 = new RequiredProductTypeDTO();
        requiredProductTypeDTO1.setId(1L);
        RequiredProductTypeDTO requiredProductTypeDTO2 = new RequiredProductTypeDTO();
        assertThat(requiredProductTypeDTO1).isNotEqualTo(requiredProductTypeDTO2);
        requiredProductTypeDTO2.setId(requiredProductTypeDTO1.getId());
        assertThat(requiredProductTypeDTO1).isEqualTo(requiredProductTypeDTO2);
        requiredProductTypeDTO2.setId(2L);
        assertThat(requiredProductTypeDTO1).isNotEqualTo(requiredProductTypeDTO2);
        requiredProductTypeDTO1.setId(null);
        assertThat(requiredProductTypeDTO1).isNotEqualTo(requiredProductTypeDTO2);
    }
}
