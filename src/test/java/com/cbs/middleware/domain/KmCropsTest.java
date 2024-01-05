package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.KmCropsTestSamples.*;
import static com.cbs.middleware.domain.KmDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmCropsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmCrops.class);
        KmCrops kmCrops1 = getKmCropsSample1();
        KmCrops kmCrops2 = new KmCrops();
        assertThat(kmCrops1).isNotEqualTo(kmCrops2);

        kmCrops2.setId(kmCrops1.getId());
        assertThat(kmCrops1).isEqualTo(kmCrops2);

        kmCrops2 = getKmCropsSample2();
        assertThat(kmCrops1).isNotEqualTo(kmCrops2);
    }

    @Test
    void kmDetailsTest() throws Exception {
        KmCrops kmCrops = getKmCropsRandomSampleGenerator();
        KmDetails kmDetailsBack = getKmDetailsRandomSampleGenerator();

        kmCrops.setKmDetails(kmDetailsBack);
        assertThat(kmCrops.getKmDetails()).isEqualTo(kmDetailsBack);

        kmCrops.kmDetails(null);
        assertThat(kmCrops.getKmDetails()).isNull();
    }
}
