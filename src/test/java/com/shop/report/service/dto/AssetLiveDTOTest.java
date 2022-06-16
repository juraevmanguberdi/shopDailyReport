package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetLiveDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetLiveDTO.class);
        AssetLiveDTO assetLiveDTO1 = new AssetLiveDTO();
        assetLiveDTO1.setId(1L);
        AssetLiveDTO assetLiveDTO2 = new AssetLiveDTO();
        assertThat(assetLiveDTO1).isNotEqualTo(assetLiveDTO2);
        assetLiveDTO2.setId(assetLiveDTO1.getId());
        assertThat(assetLiveDTO1).isEqualTo(assetLiveDTO2);
        assetLiveDTO2.setId(2L);
        assertThat(assetLiveDTO1).isNotEqualTo(assetLiveDTO2);
        assetLiveDTO1.setId(null);
        assertThat(assetLiveDTO1).isNotEqualTo(assetLiveDTO2);
    }
}
