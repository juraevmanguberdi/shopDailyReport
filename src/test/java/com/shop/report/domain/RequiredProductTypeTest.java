package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequiredProductTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequiredProductType.class);
        RequiredProductType requiredProductType1 = new RequiredProductType();
        requiredProductType1.setId(1L);
        RequiredProductType requiredProductType2 = new RequiredProductType();
        requiredProductType2.setId(requiredProductType1.getId());
        assertThat(requiredProductType1).isEqualTo(requiredProductType2);
        requiredProductType2.setId(2L);
        assertThat(requiredProductType1).isNotEqualTo(requiredProductType2);
        requiredProductType1.setId(null);
        assertThat(requiredProductType1).isNotEqualTo(requiredProductType2);
    }
}
