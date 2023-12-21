package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.FactoryMasterTestSamples.*;
import static com.cbs.middleware.domain.KarkhanaVasuliFileTestSamples.*;
import static com.cbs.middleware.domain.KarkhanaVasuliRecordsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class KarkhanaVasuliFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KarkhanaVasuliFile.class);
        KarkhanaVasuliFile karkhanaVasuliFile1 = getKarkhanaVasuliFileSample1();
        KarkhanaVasuliFile karkhanaVasuliFile2 = new KarkhanaVasuliFile();
        assertThat(karkhanaVasuliFile1).isNotEqualTo(karkhanaVasuliFile2);

        karkhanaVasuliFile2.setId(karkhanaVasuliFile1.getId());
        assertThat(karkhanaVasuliFile1).isEqualTo(karkhanaVasuliFile2);

        karkhanaVasuliFile2 = getKarkhanaVasuliFileSample2();
        assertThat(karkhanaVasuliFile1).isNotEqualTo(karkhanaVasuliFile2);
    }

    @Test
    void factoryMasterTest() throws Exception {
        KarkhanaVasuliFile karkhanaVasuliFile = getKarkhanaVasuliFileRandomSampleGenerator();
        FactoryMaster factoryMasterBack = getFactoryMasterRandomSampleGenerator();

        karkhanaVasuliFile.setFactoryMaster(factoryMasterBack);
        assertThat(karkhanaVasuliFile.getFactoryMaster()).isEqualTo(factoryMasterBack);

        karkhanaVasuliFile.factoryMaster(null);
        assertThat(karkhanaVasuliFile.getFactoryMaster()).isNull();
    }

    @Test
    void karkhanaVasuliRecordsTest() throws Exception {
        KarkhanaVasuliFile karkhanaVasuliFile = getKarkhanaVasuliFileRandomSampleGenerator();
        KarkhanaVasuliRecords karkhanaVasuliRecordsBack = getKarkhanaVasuliRecordsRandomSampleGenerator();

        karkhanaVasuliFile.addKarkhanaVasuliRecords(karkhanaVasuliRecordsBack);
        assertThat(karkhanaVasuliFile.getKarkhanaVasuliRecords()).containsOnly(karkhanaVasuliRecordsBack);
        assertThat(karkhanaVasuliRecordsBack.getKarkhanaVasuliFile()).isEqualTo(karkhanaVasuliFile);

        karkhanaVasuliFile.removeKarkhanaVasuliRecords(karkhanaVasuliRecordsBack);
        assertThat(karkhanaVasuliFile.getKarkhanaVasuliRecords()).doesNotContain(karkhanaVasuliRecordsBack);
        assertThat(karkhanaVasuliRecordsBack.getKarkhanaVasuliFile()).isNull();

        karkhanaVasuliFile.karkhanaVasuliRecords(new HashSet<>(Set.of(karkhanaVasuliRecordsBack)));
        assertThat(karkhanaVasuliFile.getKarkhanaVasuliRecords()).containsOnly(karkhanaVasuliRecordsBack);
        assertThat(karkhanaVasuliRecordsBack.getKarkhanaVasuliFile()).isEqualTo(karkhanaVasuliFile);

        karkhanaVasuliFile.setKarkhanaVasuliRecords(new HashSet<>());
        assertThat(karkhanaVasuliFile.getKarkhanaVasuliRecords()).doesNotContain(karkhanaVasuliRecordsBack);
        assertThat(karkhanaVasuliRecordsBack.getKarkhanaVasuliFile()).isNull();
    }
}
