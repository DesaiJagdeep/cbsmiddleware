package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KamalCropTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KamalCrop.class);
        KamalCrop kamalCrop1 = new KamalCrop();
        kamalCrop1.setId(1L);
        KamalCrop kamalCrop2 = new KamalCrop();
        kamalCrop2.setId(kamalCrop1.getId());
        assertThat(kamalCrop1).isEqualTo(kamalCrop2);
        kamalCrop2.setId(2L);
        assertThat(kamalCrop1).isNotEqualTo(kamalCrop2);
        kamalCrop1.setId(null);
        assertThat(kamalCrop1).isNotEqualTo(kamalCrop2);
    }
}
