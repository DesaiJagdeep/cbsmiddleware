package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationLogHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationLogHistory.class);
        ApplicationLogHistory applicationLogHistory1 = new ApplicationLogHistory();
        applicationLogHistory1.setId(1L);
        ApplicationLogHistory applicationLogHistory2 = new ApplicationLogHistory();
        applicationLogHistory2.setId(applicationLogHistory1.getId());
        assertThat(applicationLogHistory1).isEqualTo(applicationLogHistory2);
        applicationLogHistory2.setId(2L);
        assertThat(applicationLogHistory1).isNotEqualTo(applicationLogHistory2);
        applicationLogHistory1.setId(null);
        assertThat(applicationLogHistory1).isNotEqualTo(applicationLogHistory2);
    }
}
