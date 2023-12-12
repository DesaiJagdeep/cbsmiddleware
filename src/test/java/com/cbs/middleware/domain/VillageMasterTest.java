package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.VillageMasterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VillageMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VillageMaster.class);
        VillageMaster villageMaster1 = getVillageMasterSample1();
        VillageMaster villageMaster2 = new VillageMaster();
        assertThat(villageMaster1).isNotEqualTo(villageMaster2);

        villageMaster2.setId(villageMaster1.getId());
        assertThat(villageMaster1).isEqualTo(villageMaster2);

        villageMaster2 = getVillageMasterSample2();
        assertThat(villageMaster1).isNotEqualTo(villageMaster2);
    }

//    @Test
//    void talukaMasterTest() throws Exception {
//        VillageMaster villageMaster = getVillageMasterRandomSampleGenerator();
//        TalukaMaster talukaMasterBack = getTalukaMasterRandomSampleGenerator();
//
//        villageMaster.setTalukaMaster(talukaMasterBack);
//        assertThat(villageMaster.getTalukaMaster()).isEqualTo(talukaMasterBack);
//
//        villageMaster.talukaMaster(null);
//        assertThat(villageMaster.getTalukaMaster()).isNull();
//    }
}
