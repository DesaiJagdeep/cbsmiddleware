package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactoryMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactoryMaster.class);
        FactoryMaster factoryMaster1 = new FactoryMaster();
        factoryMaster1.setId(1L);
        FactoryMaster factoryMaster2 = new FactoryMaster();
        factoryMaster2.setId(factoryMaster1.getId());
        assertThat(factoryMaster1).isEqualTo(factoryMaster2);
        factoryMaster2.setId(2L);
        assertThat(factoryMaster1).isNotEqualTo(factoryMaster2);
        factoryMaster1.setId(null);
        assertThat(factoryMaster1).isNotEqualTo(factoryMaster2);
    }
}
