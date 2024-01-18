package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KarkhanaVasuliRecordsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KarkhanaVasuliRecords.class);
        KarkhanaVasuliRecords karkhanaVasuliRecords1 = new KarkhanaVasuliRecords();
        karkhanaVasuliRecords1.setId(1L);
        KarkhanaVasuliRecords karkhanaVasuliRecords2 = new KarkhanaVasuliRecords();
        karkhanaVasuliRecords2.setId(karkhanaVasuliRecords1.getId());
        assertThat(karkhanaVasuliRecords1).isEqualTo(karkhanaVasuliRecords2);
        karkhanaVasuliRecords2.setId(2L);
        assertThat(karkhanaVasuliRecords1).isNotEqualTo(karkhanaVasuliRecords2);
        karkhanaVasuliRecords1.setId(null);
        assertThat(karkhanaVasuliRecords1).isNotEqualTo(karkhanaVasuliRecords2);
    }
}
