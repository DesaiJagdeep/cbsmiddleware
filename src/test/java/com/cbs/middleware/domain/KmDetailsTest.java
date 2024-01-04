package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.KmDetailsTestSamples.*;
import static com.cbs.middleware.domain.KmMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmDetails.class);
        KmDetails kmDetails1 = getKmDetailsSample1();
        KmDetails kmDetails2 = new KmDetails();
        assertThat(kmDetails1).isNotEqualTo(kmDetails2);

        kmDetails2.setId(kmDetails1.getId());
        assertThat(kmDetails1).isEqualTo(kmDetails2);

        kmDetails2 = getKmDetailsSample2();
        assertThat(kmDetails1).isNotEqualTo(kmDetails2);
    }

    @Test
    void kmMasterTest() throws Exception {
        KmDetails kmDetails = getKmDetailsRandomSampleGenerator();
        KmMaster kmMasterBack = getKmMasterRandomSampleGenerator();

        kmDetails.setKmMaster(kmMasterBack);
        assertThat(kmDetails.getKmMaster()).isEqualTo(kmMasterBack);

        kmDetails.kmMaster(null);
        assertThat(kmDetails.getKmMaster()).isNull();
    }
}
