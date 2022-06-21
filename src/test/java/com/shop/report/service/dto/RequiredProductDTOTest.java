package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequiredProductDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequiredProductDTO.class);
        RequiredProductDTO requiredProductDTO1 = new RequiredProductDTO();
        requiredProductDTO1.setId(1L);
        RequiredProductDTO requiredProductDTO2 = new RequiredProductDTO();
        assertThat(requiredProductDTO1).isNotEqualTo(requiredProductDTO2);
        requiredProductDTO2.setId(requiredProductDTO1.getId());
        assertThat(requiredProductDTO1).isEqualTo(requiredProductDTO2);
        requiredProductDTO2.setId(2L);
        assertThat(requiredProductDTO1).isNotEqualTo(requiredProductDTO2);
        requiredProductDTO1.setId(null);
        assertThat(requiredProductDTO1).isNotEqualTo(requiredProductDTO2);
    }
}
