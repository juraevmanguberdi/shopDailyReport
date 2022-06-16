package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetRegistryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetRegistry.class);
        AssetRegistry assetRegistry1 = new AssetRegistry();
        assetRegistry1.setId(1L);
        AssetRegistry assetRegistry2 = new AssetRegistry();
        assetRegistry2.setId(assetRegistry1.getId());
        assertThat(assetRegistry1).isEqualTo(assetRegistry2);
        assetRegistry2.setId(2L);
        assertThat(assetRegistry1).isNotEqualTo(assetRegistry2);
        assetRegistry1.setId(null);
        assertThat(assetRegistry1).isNotEqualTo(assetRegistry2);
    }
}
