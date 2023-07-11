package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StateMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StateMaster.class);
        StateMaster stateMaster1 = new StateMaster();
        stateMaster1.setId(1L);
        StateMaster stateMaster2 = new StateMaster();
        stateMaster2.setId(stateMaster1.getId());
        assertThat(stateMaster1).isEqualTo(stateMaster2);
        stateMaster2.setId(2L);
        assertThat(stateMaster1).isNotEqualTo(stateMaster2);
        stateMaster1.setId(null);
        assertThat(stateMaster1).isNotEqualTo(stateMaster2);
    }
}
