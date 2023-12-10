package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.FactoryMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactoryMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactoryMaster.class);
        FactoryMaster factoryMaster1 = getFactoryMasterSample1();
        FactoryMaster factoryMaster2 = new FactoryMaster();
        assertThat(factoryMaster1).isNotEqualTo(factoryMaster2);

        factoryMaster2.setId(factoryMaster1.getId());
        assertThat(factoryMaster1).isEqualTo(factoryMaster2);

        factoryMaster2 = getFactoryMasterSample2();
        assertThat(factoryMaster1).isNotEqualTo(factoryMaster2);
    }
}
