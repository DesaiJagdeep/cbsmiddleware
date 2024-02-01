package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CropRateMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CropRateMaster.class);
        CropRateMaster cropRateMaster1 = new CropRateMaster();
        cropRateMaster1.setId(1L);
        CropRateMaster cropRateMaster2 = new CropRateMaster();
        cropRateMaster2.setId(cropRateMaster1.getId());
        assertThat(cropRateMaster1).isEqualTo(cropRateMaster2);
        cropRateMaster2.setId(2L);
        assertThat(cropRateMaster1).isNotEqualTo(cropRateMaster2);
        cropRateMaster1.setId(null);
        assertThat(cropRateMaster1).isNotEqualTo(cropRateMaster2);
    }
}
