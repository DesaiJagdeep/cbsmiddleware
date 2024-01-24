package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KamalSocietyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KamalSociety.class);
        KamalSociety kamalSociety1 = new KamalSociety();
        kamalSociety1.setId(1L);
        KamalSociety kamalSociety2 = new KamalSociety();
        kamalSociety2.setId(kamalSociety1.getId());
        assertThat(kamalSociety1).isEqualTo(kamalSociety2);
        kamalSociety2.setId(2L);
        assertThat(kamalSociety1).isNotEqualTo(kamalSociety2);
        kamalSociety1.setId(null);
        assertThat(kamalSociety1).isNotEqualTo(kamalSociety2);
    }
}
