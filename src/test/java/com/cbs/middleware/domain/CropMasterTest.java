package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CropMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CropMaster.class);
        CropMaster cropMaster1 = new CropMaster();
        cropMaster1.setId(1L);
        CropMaster cropMaster2 = new CropMaster();
        cropMaster2.setId(cropMaster1.getId());
        assertThat(cropMaster1).isEqualTo(cropMaster2);
        cropMaster2.setId(2L);
        assertThat(cropMaster1).isNotEqualTo(cropMaster2);
        cropMaster1.setId(null);
        assertThat(cropMaster1).isNotEqualTo(cropMaster2);
    }
}
