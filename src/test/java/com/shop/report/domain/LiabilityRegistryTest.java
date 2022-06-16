package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LiabilityRegistryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LiabilityRegistry.class);
        LiabilityRegistry liabilityRegistry1 = new LiabilityRegistry();
        liabilityRegistry1.setId(1L);
        LiabilityRegistry liabilityRegistry2 = new LiabilityRegistry();
        liabilityRegistry2.setId(liabilityRegistry1.getId());
        assertThat(liabilityRegistry1).isEqualTo(liabilityRegistry2);
        liabilityRegistry2.setId(2L);
        assertThat(liabilityRegistry1).isNotEqualTo(liabilityRegistry2);
        liabilityRegistry1.setId(null);
        assertThat(liabilityRegistry1).isNotEqualTo(liabilityRegistry2);
    }
}
