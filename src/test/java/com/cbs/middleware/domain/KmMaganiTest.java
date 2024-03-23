package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmMaganiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmMagani.class);
        KmMagani kmMagani1 = new KmMagani();
        kmMagani1.setId(1L);
        KmMagani kmMagani2 = new KmMagani();
        kmMagani2.setId(kmMagani1.getId());
        assertThat(kmMagani1).isEqualTo(kmMagani2);
        kmMagani2.setId(2L);
        assertThat(kmMagani1).isNotEqualTo(kmMagani2);
        kmMagani1.setId(null);
        assertThat(kmMagani1).isNotEqualTo(kmMagani2);
    }
}
