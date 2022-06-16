package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetRegistryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetRegistryDTO.class);
        AssetRegistryDTO assetRegistryDTO1 = new AssetRegistryDTO();
        assetRegistryDTO1.setId(1L);
        AssetRegistryDTO assetRegistryDTO2 = new AssetRegistryDTO();
        assertThat(assetRegistryDTO1).isNotEqualTo(assetRegistryDTO2);
        assetRegistryDTO2.setId(assetRegistryDTO1.getId());
        assertThat(assetRegistryDTO1).isEqualTo(assetRegistryDTO2);
        assetRegistryDTO2.setId(2L);
        assertThat(assetRegistryDTO1).isNotEqualTo(assetRegistryDTO2);
        assetRegistryDTO1.setId(null);
        assertThat(assetRegistryDTO1).isNotEqualTo(assetRegistryDTO2);
    }
}
