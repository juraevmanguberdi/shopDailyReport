package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DailyRegistryShopTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyRegistryShop.class);
        DailyRegistryShop dailyRegistryShop1 = new DailyRegistryShop();
        dailyRegistryShop1.setId(1L);
        DailyRegistryShop dailyRegistryShop2 = new DailyRegistryShop();
        dailyRegistryShop2.setId(dailyRegistryShop1.getId());
        assertThat(dailyRegistryShop1).isEqualTo(dailyRegistryShop2);
        dailyRegistryShop2.setId(2L);
        assertThat(dailyRegistryShop1).isNotEqualTo(dailyRegistryShop2);
        dailyRegistryShop1.setId(null);
        assertThat(dailyRegistryShop1).isNotEqualTo(dailyRegistryShop2);
    }
}
