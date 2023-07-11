package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandTypeMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LandTypeMaster.class);
        LandTypeMaster landTypeMaster1 = new LandTypeMaster();
        landTypeMaster1.setId(1L);
        LandTypeMaster landTypeMaster2 = new LandTypeMaster();
        landTypeMaster2.setId(landTypeMaster1.getId());
        assertThat(landTypeMaster1).isEqualTo(landTypeMaster2);
        landTypeMaster2.setId(2L);
        assertThat(landTypeMaster1).isNotEqualTo(landTypeMaster2);
        landTypeMaster1.setId(null);
        assertThat(landTypeMaster1).isNotEqualTo(landTypeMaster2);
    }
}
