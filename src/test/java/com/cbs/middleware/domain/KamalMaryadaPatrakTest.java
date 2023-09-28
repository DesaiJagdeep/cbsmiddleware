package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KamalMaryadaPatrakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KamalMaryadaPatrak.class);
        KamalMaryadaPatrak kamalMaryadaPatrak1 = new KamalMaryadaPatrak();
        kamalMaryadaPatrak1.setId(1L);
        KamalMaryadaPatrak kamalMaryadaPatrak2 = new KamalMaryadaPatrak();
        kamalMaryadaPatrak2.setId(kamalMaryadaPatrak1.getId());
        assertThat(kamalMaryadaPatrak1).isEqualTo(kamalMaryadaPatrak2);
        kamalMaryadaPatrak2.setId(2L);
        assertThat(kamalMaryadaPatrak1).isNotEqualTo(kamalMaryadaPatrak2);
        kamalMaryadaPatrak1.setId(null);
        assertThat(kamalMaryadaPatrak1).isNotEqualTo(kamalMaryadaPatrak2);
    }
}
