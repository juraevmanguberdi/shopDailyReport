package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LiabilityLiveDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LiabilityLiveDTO.class);
        LiabilityLiveDTO liabilityLiveDTO1 = new LiabilityLiveDTO();
        liabilityLiveDTO1.setId(1L);
        LiabilityLiveDTO liabilityLiveDTO2 = new LiabilityLiveDTO();
        assertThat(liabilityLiveDTO1).isNotEqualTo(liabilityLiveDTO2);
        liabilityLiveDTO2.setId(liabilityLiveDTO1.getId());
        assertThat(liabilityLiveDTO1).isEqualTo(liabilityLiveDTO2);
        liabilityLiveDTO2.setId(2L);
        assertThat(liabilityLiveDTO1).isNotEqualTo(liabilityLiveDTO2);
        liabilityLiveDTO1.setId(null);
        assertThat(liabilityLiveDTO1).isNotEqualTo(liabilityLiveDTO2);
    }
}
