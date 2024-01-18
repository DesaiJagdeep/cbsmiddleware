package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmCropsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmCrops.class);
        KmCrops kmCrops1 = new KmCrops();
        kmCrops1.setId(1L);
        KmCrops kmCrops2 = new KmCrops();
        kmCrops2.setId(kmCrops1.getId());
        assertThat(kmCrops1).isEqualTo(kmCrops2);
        kmCrops2.setId(2L);
        assertThat(kmCrops1).isNotEqualTo(kmCrops2);
        kmCrops1.setId(null);
        assertThat(kmCrops1).isNotEqualTo(kmCrops2);
    }
}
