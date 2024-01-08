package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VillageMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VillageMaster.class);
        VillageMaster villageMaster1 = new VillageMaster();
        villageMaster1.setId(1L);
        VillageMaster villageMaster2 = new VillageMaster();
        villageMaster2.setId(villageMaster1.getId());
        assertThat(villageMaster1).isEqualTo(villageMaster2);
        villageMaster2.setId(2L);
        assertThat(villageMaster1).isNotEqualTo(villageMaster2);
        villageMaster1.setId(null);
        assertThat(villageMaster1).isNotEqualTo(villageMaster2);
    }
}
