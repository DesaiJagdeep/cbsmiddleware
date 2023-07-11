package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OccupationMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OccupationMaster.class);
        OccupationMaster occupationMaster1 = new OccupationMaster();
        occupationMaster1.setId(1L);
        OccupationMaster occupationMaster2 = new OccupationMaster();
        occupationMaster2.setId(occupationMaster1.getId());
        assertThat(occupationMaster1).isEqualTo(occupationMaster2);
        occupationMaster2.setId(2L);
        assertThat(occupationMaster1).isNotEqualTo(occupationMaster2);
        occupationMaster1.setId(null);
        assertThat(occupationMaster1).isNotEqualTo(occupationMaster2);
    }
}
