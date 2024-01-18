package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KarkhanaVasuliFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KarkhanaVasuliFile.class);
        KarkhanaVasuliFile karkhanaVasuliFile1 = new KarkhanaVasuliFile();
        karkhanaVasuliFile1.setId(1L);
        KarkhanaVasuliFile karkhanaVasuliFile2 = new KarkhanaVasuliFile();
        karkhanaVasuliFile2.setId(karkhanaVasuliFile1.getId());
        assertThat(karkhanaVasuliFile1).isEqualTo(karkhanaVasuliFile2);
        karkhanaVasuliFile2.setId(2L);
        assertThat(karkhanaVasuliFile1).isNotEqualTo(karkhanaVasuliFile2);
        karkhanaVasuliFile1.setId(null);
        assertThat(karkhanaVasuliFile1).isNotEqualTo(karkhanaVasuliFile2);
    }
}
