package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourtCaseSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourtCaseSetting.class);
        CourtCaseSetting courtCaseSetting1 = new CourtCaseSetting();
        courtCaseSetting1.setId(1L);
        CourtCaseSetting courtCaseSetting2 = new CourtCaseSetting();
        courtCaseSetting2.setId(courtCaseSetting1.getId());
        assertThat(courtCaseSetting1).isEqualTo(courtCaseSetting2);
        courtCaseSetting2.setId(2L);
        assertThat(courtCaseSetting1).isNotEqualTo(courtCaseSetting2);
        courtCaseSetting1.setId(null);
        assertThat(courtCaseSetting1).isNotEqualTo(courtCaseSetting2);
    }
}
