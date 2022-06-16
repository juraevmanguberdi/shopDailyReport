package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetLiveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetLive.class);
        AssetLive assetLive1 = new AssetLive();
        assetLive1.setId(1L);
        AssetLive assetLive2 = new AssetLive();
        assetLive2.setId(assetLive1.getId());
        assertThat(assetLive1).isEqualTo(assetLive2);
        assetLive2.setId(2L);
        assertThat(assetLive1).isNotEqualTo(assetLive2);
        assetLive1.setId(null);
        assertThat(assetLive1).isNotEqualTo(assetLive2);
    }
}
