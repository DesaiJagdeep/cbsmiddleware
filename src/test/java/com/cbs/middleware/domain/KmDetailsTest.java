package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmDetails.class);
        KmDetails kmDetails1 = new KmDetails();
        kmDetails1.setId(1L);
        KmDetails kmDetails2 = new KmDetails();
        kmDetails2.setId(kmDetails1.getId());
        assertThat(kmDetails1).isEqualTo(kmDetails2);
        kmDetails2.setId(2L);
        assertThat(kmDetails1).isNotEqualTo(kmDetails2);
        kmDetails1.setId(null);
        assertThat(kmDetails1).isNotEqualTo(kmDetails2);
    }
}
