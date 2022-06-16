package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LiabilityRegistryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LiabilityRegistryDTO.class);
        LiabilityRegistryDTO liabilityRegistryDTO1 = new LiabilityRegistryDTO();
        liabilityRegistryDTO1.setId(1L);
        LiabilityRegistryDTO liabilityRegistryDTO2 = new LiabilityRegistryDTO();
        assertThat(liabilityRegistryDTO1).isNotEqualTo(liabilityRegistryDTO2);
        liabilityRegistryDTO2.setId(liabilityRegistryDTO1.getId());
        assertThat(liabilityRegistryDTO1).isEqualTo(liabilityRegistryDTO2);
        liabilityRegistryDTO2.setId(2L);
        assertThat(liabilityRegistryDTO1).isNotEqualTo(liabilityRegistryDTO2);
        liabilityRegistryDTO1.setId(null);
        assertThat(liabilityRegistryDTO1).isNotEqualTo(liabilityRegistryDTO2);
    }
}
