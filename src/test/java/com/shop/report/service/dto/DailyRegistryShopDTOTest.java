package com.shop.report.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyRegistryShopDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyRegistryShopDTO.class);
        DailyRegistryShopDTO dailyRegistryShopDTO1 = new DailyRegistryShopDTO();
        dailyRegistryShopDTO1.setId(1L);
        DailyRegistryShopDTO dailyRegistryShopDTO2 = new DailyRegistryShopDTO();
        assertThat(dailyRegistryShopDTO1).isNotEqualTo(dailyRegistryShopDTO2);
        dailyRegistryShopDTO2.setId(dailyRegistryShopDTO1.getId());
        assertThat(dailyRegistryShopDTO1).isEqualTo(dailyRegistryShopDTO2);
        dailyRegistryShopDTO2.setId(2L);
        assertThat(dailyRegistryShopDTO1).isNotEqualTo(dailyRegistryShopDTO2);
        dailyRegistryShopDTO1.setId(null);
        assertThat(dailyRegistryShopDTO1).isNotEqualTo(dailyRegistryShopDTO2);
    }
}
