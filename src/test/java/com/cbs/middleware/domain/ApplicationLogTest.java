package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationLog.class);
        ApplicationLog applicationLog1 = new ApplicationLog();
        applicationLog1.setId(1L);
        ApplicationLog applicationLog2 = new ApplicationLog();
        applicationLog2.setId(applicationLog1.getId());
        assertThat(applicationLog1).isEqualTo(applicationLog2);
        applicationLog2.setId(2L);
        assertThat(applicationLog1).isNotEqualTo(applicationLog2);
        applicationLog1.setId(null);
        assertThat(applicationLog1).isNotEqualTo(applicationLog2);
    }
}
