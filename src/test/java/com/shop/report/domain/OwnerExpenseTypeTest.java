package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OwnerExpenseTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerExpenseType.class);
        OwnerExpenseType ownerExpenseType1 = new OwnerExpenseType();
        ownerExpenseType1.setId(1L);
        OwnerExpenseType ownerExpenseType2 = new OwnerExpenseType();
        ownerExpenseType2.setId(ownerExpenseType1.getId());
        assertThat(ownerExpenseType1).isEqualTo(ownerExpenseType2);
        ownerExpenseType2.setId(2L);
        assertThat(ownerExpenseType1).isNotEqualTo(ownerExpenseType2);
        ownerExpenseType1.setId(null);
        assertThat(ownerExpenseType1).isNotEqualTo(ownerExpenseType2);
    }
}
