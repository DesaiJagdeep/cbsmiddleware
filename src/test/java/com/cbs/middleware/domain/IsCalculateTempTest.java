package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IsCalculateTempTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IsCalculateTemp.class);
        IsCalculateTemp isCalculateTemp1 = new IsCalculateTemp();
        isCalculateTemp1.setId(1L);
        IsCalculateTemp isCalculateTemp2 = new IsCalculateTemp();
        isCalculateTemp2.setId(isCalculateTemp1.getId());
        assertThat(isCalculateTemp1).isEqualTo(isCalculateTemp2);
        isCalculateTemp2.setId(2L);
        assertThat(isCalculateTemp1).isNotEqualTo(isCalculateTemp2);
        isCalculateTemp1.setId(null);
        assertThat(isCalculateTemp1).isNotEqualTo(isCalculateTemp2);
    }
}
