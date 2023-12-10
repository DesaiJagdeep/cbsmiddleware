package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.KarkhanaVasuliFileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KarkhanaVasuliFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KarkhanaVasuliFile.class);
        KarkhanaVasuliFile karkhanaVasuliFile1 = getKarkhanaVasuliFileSample1();
        KarkhanaVasuliFile karkhanaVasuliFile2 = new KarkhanaVasuliFile();
        assertThat(karkhanaVasuliFile1).isNotEqualTo(karkhanaVasuliFile2);

        karkhanaVasuliFile2.setId(karkhanaVasuliFile1.getId());
        assertThat(karkhanaVasuliFile1).isEqualTo(karkhanaVasuliFile2);

        karkhanaVasuliFile2 = getKarkhanaVasuliFileSample2();
        assertThat(karkhanaVasuliFile1).isNotEqualTo(karkhanaVasuliFile2);
    }
}
