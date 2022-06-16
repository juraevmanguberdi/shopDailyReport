package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OwnerExpenseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OwnerExpense.class);
        OwnerExpense ownerExpense1 = new OwnerExpense();
        ownerExpense1.setId(1L);
        OwnerExpense ownerExpense2 = new OwnerExpense();
        ownerExpense2.setId(ownerExpense1.getId());
        assertThat(ownerExpense1).isEqualTo(ownerExpense2);
        ownerExpense2.setId(2L);
        assertThat(ownerExpense1).isNotEqualTo(ownerExpense2);
        ownerExpense1.setId(null);
        assertThat(ownerExpense1).isNotEqualTo(ownerExpense2);
    }
}
