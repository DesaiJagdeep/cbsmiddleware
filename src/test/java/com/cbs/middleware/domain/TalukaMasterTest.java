package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TalukaMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TalukaMaster.class);
        TalukaMaster talukaMaster1 = new TalukaMaster();
        talukaMaster1.setId(1L);
        TalukaMaster talukaMaster2 = new TalukaMaster();
        talukaMaster2.setId(talukaMaster1.getId());
        assertThat(talukaMaster1).isEqualTo(talukaMaster2);
        talukaMaster2.setId(2L);
        assertThat(talukaMaster1).isNotEqualTo(talukaMaster2);
        talukaMaster1.setId(null);
        assertThat(talukaMaster1).isNotEqualTo(talukaMaster2);
    }
}
