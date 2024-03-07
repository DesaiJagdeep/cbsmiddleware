package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmMaganiCropTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmMaganiCrop.class);
        KmMaganiCrop kmMaganiCrop1 = new KmMaganiCrop();
        kmMaganiCrop1.setId(1L);
        KmMaganiCrop kmMaganiCrop2 = new KmMaganiCrop();
        kmMaganiCrop2.setId(kmMaganiCrop1.getId());
        assertThat(kmMaganiCrop1).isEqualTo(kmMaganiCrop2);
        kmMaganiCrop2.setId(2L);
        assertThat(kmMaganiCrop1).isNotEqualTo(kmMaganiCrop2);
        kmMaganiCrop1.setId(null);
        assertThat(kmMaganiCrop1).isNotEqualTo(kmMaganiCrop2);
    }
}
