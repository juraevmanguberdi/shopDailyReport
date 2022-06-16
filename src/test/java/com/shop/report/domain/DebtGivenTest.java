package com.shop.report.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.shop.report.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DebtGivenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DebtGiven.class);
        DebtGiven debtGiven1 = new DebtGiven();
        debtGiven1.setId(1L);
        DebtGiven debtGiven2 = new DebtGiven();
        debtGiven2.setId(debtGiven1.getId());
        assertThat(debtGiven1).isEqualTo(debtGiven2);
        debtGiven2.setId(2L);
        assertThat(debtGiven1).isNotEqualTo(debtGiven2);
        debtGiven1.setId(null);
        assertThat(debtGiven1).isNotEqualTo(debtGiven2);
    }
}
