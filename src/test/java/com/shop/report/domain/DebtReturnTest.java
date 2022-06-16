package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DebtReturnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebtReturn.class);
        DebtReturn debtReturn1 = new DebtReturn();
        debtReturn1.setId(1L);
        DebtReturn debtReturn2 = new DebtReturn();
        debtReturn2.setId(debtReturn1.getId());
        assertThat(debtReturn1).isEqualTo(debtReturn2);
        debtReturn2.setId(2L);
        assertThat(debtReturn1).isNotEqualTo(debtReturn2);
        debtReturn1.setId(null);
        assertThat(debtReturn1).isNotEqualTo(debtReturn2);
    }
}
