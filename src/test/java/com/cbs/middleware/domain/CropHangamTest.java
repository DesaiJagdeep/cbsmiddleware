package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CropHangamTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CropHangam.class);
        CropHangam cropHangam1 = new CropHangam();
        cropHangam1.setId(1L);
        CropHangam cropHangam2 = new CropHangam();
        cropHangam2.setId(cropHangam1.getId());
        assertThat(cropHangam1).isEqualTo(cropHangam2);
        cropHangam2.setId(2L);
        assertThat(cropHangam1).isNotEqualTo(cropHangam2);
        cropHangam1.setId(null);
        assertThat(cropHangam1).isNotEqualTo(cropHangam2);
    }
}
