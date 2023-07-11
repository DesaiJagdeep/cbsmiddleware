package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DesignationMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignationMaster.class);
        DesignationMaster designationMaster1 = new DesignationMaster();
        designationMaster1.setId(1L);
        DesignationMaster designationMaster2 = new DesignationMaster();
        designationMaster2.setId(designationMaster1.getId());
        assertThat(designationMaster1).isEqualTo(designationMaster2);
        designationMaster2.setId(2L);
        assertThat(designationMaster1).isNotEqualTo(designationMaster2);
        designationMaster1.setId(null);
        assertThat(designationMaster1).isNotEqualTo(designationMaster2);
    }
}
