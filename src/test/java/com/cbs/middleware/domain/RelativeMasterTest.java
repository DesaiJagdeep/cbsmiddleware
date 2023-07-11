package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelativeMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelativeMaster.class);
        RelativeMaster relativeMaster1 = new RelativeMaster();
        relativeMaster1.setId(1L);
        RelativeMaster relativeMaster2 = new RelativeMaster();
        relativeMaster2.setId(relativeMaster1.getId());
        assertThat(relativeMaster1).isEqualTo(relativeMaster2);
        relativeMaster2.setId(2L);
        assertThat(relativeMaster1).isNotEqualTo(relativeMaster2);
        relativeMaster1.setId(null);
        assertThat(relativeMaster1).isNotEqualTo(relativeMaster2);
    }
}
