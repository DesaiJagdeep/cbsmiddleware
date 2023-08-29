package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CourtCaseDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourtCaseDetails.class);
        CourtCaseDetails courtCaseDetails1 = new CourtCaseDetails();
        courtCaseDetails1.setId(1L);
        CourtCaseDetails courtCaseDetails2 = new CourtCaseDetails();
        courtCaseDetails2.setId(courtCaseDetails1.getId());
        assertThat(courtCaseDetails1).isEqualTo(courtCaseDetails2);
        courtCaseDetails2.setId(2L);
        assertThat(courtCaseDetails1).isNotEqualTo(courtCaseDetails2);
        courtCaseDetails1.setId(null);
        assertThat(courtCaseDetails1).isNotEqualTo(courtCaseDetails2);
    }
}
