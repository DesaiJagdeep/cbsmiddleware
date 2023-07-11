package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FarmerTypeMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FarmerTypeMaster.class);
        FarmerTypeMaster farmerTypeMaster1 = new FarmerTypeMaster();
        farmerTypeMaster1.setId(1L);
        FarmerTypeMaster farmerTypeMaster2 = new FarmerTypeMaster();
        farmerTypeMaster2.setId(farmerTypeMaster1.getId());
        assertThat(farmerTypeMaster1).isEqualTo(farmerTypeMaster2);
        farmerTypeMaster2.setId(2L);
        assertThat(farmerTypeMaster1).isNotEqualTo(farmerTypeMaster2);
        farmerTypeMaster1.setId(null);
        assertThat(farmerTypeMaster1).isNotEqualTo(farmerTypeMaster2);
    }
}
