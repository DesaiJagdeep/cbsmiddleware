package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PacsMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PacsMaster.class);
        PacsMaster pacsMaster1 = new PacsMaster();
        pacsMaster1.setId(1L);
        PacsMaster pacsMaster2 = new PacsMaster();
        pacsMaster2.setId(pacsMaster1.getId());
        assertThat(pacsMaster1).isEqualTo(pacsMaster2);
        pacsMaster2.setId(2L);
        assertThat(pacsMaster1).isNotEqualTo(pacsMaster2);
        pacsMaster1.setId(null);
        assertThat(pacsMaster1).isNotEqualTo(pacsMaster2);
    }
}
