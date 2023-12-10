package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.KarkhanaVasuliFileTestSamples.*;
import static com.cbs.middleware.domain.KarkhanaVasuliRecordsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KarkhanaVasuliRecordsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KarkhanaVasuliRecords.class);
        KarkhanaVasuliRecords karkhanaVasuliRecords1 = getKarkhanaVasuliRecordsSample1();
        KarkhanaVasuliRecords karkhanaVasuliRecords2 = new KarkhanaVasuliRecords();
        assertThat(karkhanaVasuliRecords1).isNotEqualTo(karkhanaVasuliRecords2);

        karkhanaVasuliRecords2.setId(karkhanaVasuliRecords1.getId());
        assertThat(karkhanaVasuliRecords1).isEqualTo(karkhanaVasuliRecords2);

        karkhanaVasuliRecords2 = getKarkhanaVasuliRecordsSample2();
        assertThat(karkhanaVasuliRecords1).isNotEqualTo(karkhanaVasuliRecords2);
    }

    @Test
    void karkhanaVasuliFileTest() throws Exception {
        KarkhanaVasuliRecords karkhanaVasuliRecords = getKarkhanaVasuliRecordsRandomSampleGenerator();
        KarkhanaVasuliFile karkhanaVasuliFileBack = getKarkhanaVasuliFileRandomSampleGenerator();

        karkhanaVasuliRecords.setKarkhanaVasuliFile(karkhanaVasuliFileBack);
        assertThat(karkhanaVasuliRecords.getKarkhanaVasuliFile()).isEqualTo(karkhanaVasuliFileBack);

        karkhanaVasuliRecords.karkhanaVasuliFile(null);
        assertThat(karkhanaVasuliRecords.getKarkhanaVasuliFile()).isNull();
    }
}
