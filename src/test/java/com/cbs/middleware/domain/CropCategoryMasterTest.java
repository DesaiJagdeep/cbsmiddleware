package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CropCategoryMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CropCategoryMaster.class);
        CropCategoryMaster cropCategoryMaster1 = new CropCategoryMaster();
        cropCategoryMaster1.setId(1L);
        CropCategoryMaster cropCategoryMaster2 = new CropCategoryMaster();
        cropCategoryMaster2.setId(cropCategoryMaster1.getId());
        assertThat(cropCategoryMaster1).isEqualTo(cropCategoryMaster2);
        cropCategoryMaster2.setId(2L);
        assertThat(cropCategoryMaster1).isNotEqualTo(cropCategoryMaster2);
        cropCategoryMaster1.setId(null);
        assertThat(cropCategoryMaster1).isNotEqualTo(cropCategoryMaster2);
    }
}
