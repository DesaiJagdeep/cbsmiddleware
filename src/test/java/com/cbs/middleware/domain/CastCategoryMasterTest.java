package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CastCategoryMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CastCategoryMaster.class);
        CastCategoryMaster castCategoryMaster1 = new CastCategoryMaster();
        castCategoryMaster1.setId(1L);
        CastCategoryMaster castCategoryMaster2 = new CastCategoryMaster();
        castCategoryMaster2.setId(castCategoryMaster1.getId());
        assertThat(castCategoryMaster1).isEqualTo(castCategoryMaster2);
        castCategoryMaster2.setId(2L);
        assertThat(castCategoryMaster1).isNotEqualTo(castCategoryMaster2);
        castCategoryMaster1.setId(null);
        assertThat(castCategoryMaster1).isNotEqualTo(castCategoryMaster2);
    }
}
