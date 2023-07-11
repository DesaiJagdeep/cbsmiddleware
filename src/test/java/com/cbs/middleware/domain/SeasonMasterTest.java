package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SeasonMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeasonMaster.class);
        SeasonMaster seasonMaster1 = new SeasonMaster();
        seasonMaster1.setId(1L);
        SeasonMaster seasonMaster2 = new SeasonMaster();
        seasonMaster2.setId(seasonMaster1.getId());
        assertThat(seasonMaster1).isEqualTo(seasonMaster2);
        seasonMaster2.setId(2L);
        assertThat(seasonMaster1).isNotEqualTo(seasonMaster2);
        seasonMaster1.setId(null);
        assertThat(seasonMaster1).isNotEqualTo(seasonMaster2);
    }
}
