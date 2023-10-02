package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KarkhanaVasuliTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KarkhanaVasuli.class);
        KarkhanaVasuli karkhanaVasuli1 = new KarkhanaVasuli();
        karkhanaVasuli1.setId(1L);
        KarkhanaVasuli karkhanaVasuli2 = new KarkhanaVasuli();
        karkhanaVasuli2.setId(karkhanaVasuli1.getId());
        assertThat(karkhanaVasuli1).isEqualTo(karkhanaVasuli2);
        karkhanaVasuli2.setId(2L);
        assertThat(karkhanaVasuli1).isNotEqualTo(karkhanaVasuli2);
        karkhanaVasuli1.setId(null);
        assertThat(karkhanaVasuli1).isNotEqualTo(karkhanaVasuli2);
    }
}
