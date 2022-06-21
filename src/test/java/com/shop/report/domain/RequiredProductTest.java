package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RequiredProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequiredProduct.class);
        RequiredProduct requiredProduct1 = new RequiredProduct();
        requiredProduct1.setId(1L);
        RequiredProduct requiredProduct2 = new RequiredProduct();
        requiredProduct2.setId(requiredProduct1.getId());
        assertThat(requiredProduct1).isEqualTo(requiredProduct2);
        requiredProduct2.setId(2L);
        assertThat(requiredProduct1).isNotEqualTo(requiredProduct2);
        requiredProduct1.setId(null);
        assertThat(requiredProduct1).isNotEqualTo(requiredProduct2);
    }
}
