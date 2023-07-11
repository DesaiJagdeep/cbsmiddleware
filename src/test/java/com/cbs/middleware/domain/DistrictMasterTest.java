package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DistrictMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictMaster.class);
        DistrictMaster districtMaster1 = new DistrictMaster();
        districtMaster1.setId(1L);
        DistrictMaster districtMaster2 = new DistrictMaster();
        districtMaster2.setId(districtMaster1.getId());
        assertThat(districtMaster1).isEqualTo(districtMaster2);
        districtMaster2.setId(2L);
        assertThat(districtMaster1).isNotEqualTo(districtMaster2);
        districtMaster1.setId(null);
        assertThat(districtMaster1).isNotEqualTo(districtMaster2);
    }
}
