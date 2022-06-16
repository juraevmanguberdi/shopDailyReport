package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LiabilityLiveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LiabilityLive.class);
        LiabilityLive liabilityLive1 = new LiabilityLive();
        liabilityLive1.setId(1L);
        LiabilityLive liabilityLive2 = new LiabilityLive();
        liabilityLive2.setId(liabilityLive1.getId());
        assertThat(liabilityLive1).isEqualTo(liabilityLive2);
        liabilityLive2.setId(2L);
        assertThat(liabilityLive1).isNotEqualTo(liabilityLive2);
        liabilityLive1.setId(null);
        assertThat(liabilityLive1).isNotEqualTo(liabilityLive2);
    }
}
