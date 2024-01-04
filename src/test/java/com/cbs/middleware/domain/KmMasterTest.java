package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.KmMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmMaster.class);
        KmMaster kmMaster1 = getKmMasterSample1();
        KmMaster kmMaster2 = new KmMaster();
        assertThat(kmMaster1).isNotEqualTo(kmMaster2);

        kmMaster2.setId(kmMaster1.getId());
        assertThat(kmMaster1).isEqualTo(kmMaster2);

        kmMaster2 = getKmMasterSample2();
        assertThat(kmMaster1).isNotEqualTo(kmMaster2);
    }
}
