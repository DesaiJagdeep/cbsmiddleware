package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FarmerCategoryMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FarmerCategoryMaster.class);
        FarmerCategoryMaster farmerCategoryMaster1 = new FarmerCategoryMaster();
        farmerCategoryMaster1.setId(1L);
        FarmerCategoryMaster farmerCategoryMaster2 = new FarmerCategoryMaster();
        farmerCategoryMaster2.setId(farmerCategoryMaster1.getId());
        assertThat(farmerCategoryMaster1).isEqualTo(farmerCategoryMaster2);
        farmerCategoryMaster2.setId(2L);
        assertThat(farmerCategoryMaster1).isNotEqualTo(farmerCategoryMaster2);
        farmerCategoryMaster1.setId(null);
        assertThat(farmerCategoryMaster1).isNotEqualTo(farmerCategoryMaster2);
    }
}
