package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.CropMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CropMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CropMaster.class);
        CropMaster cropMaster1 = getCropMasterSample1();
        CropMaster cropMaster2 = new CropMaster();
        assertThat(cropMaster1).isNotEqualTo(cropMaster2);

        cropMaster2.setId(cropMaster1.getId());
        assertThat(cropMaster1).isEqualTo(cropMaster2);

        cropMaster2 = getCropMasterSample2();
        assertThat(cropMaster1).isNotEqualTo(cropMaster2);
    }
}
