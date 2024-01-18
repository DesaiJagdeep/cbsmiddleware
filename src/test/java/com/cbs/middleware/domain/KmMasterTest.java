package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmMaster.class);
        KmMaster kmMaster1 = new KmMaster();
        kmMaster1.setId(1L);
        KmMaster kmMaster2 = new KmMaster();
        kmMaster2.setId(kmMaster1.getId());
        assertThat(kmMaster1).isEqualTo(kmMaster2);
        kmMaster2.setId(2L);
        assertThat(kmMaster1).isNotEqualTo(kmMaster2);
        kmMaster1.setId(null);
        assertThat(kmMaster1).isNotEqualTo(kmMaster2);
    }
}
