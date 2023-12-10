package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KarkhanaVasuliRecordsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KarkhanaVasuliRecords getKarkhanaVasuliRecordsSample1() {
        return new KarkhanaVasuliRecords()
            .id(1L)
            .factoryMemberCode(1L)
            .karkhanaMemberCodeMr("karkhanaMemberCodeMr1")
            .memberName("memberName1")
            .memberNameMr("memberNameMr1")
            .villageName("villageName1")
            .villageNameMr("villageNameMr1")
            .amountMr("amountMr1")
            .savingAccNo(1L)
            .savingAccNoMr("savingAccNoMr1");
    }

    public static KarkhanaVasuliRecords getKarkhanaVasuliRecordsSample2() {
        return new KarkhanaVasuliRecords()
            .id(2L)
            .factoryMemberCode(2L)
            .karkhanaMemberCodeMr("karkhanaMemberCodeMr2")
            .memberName("memberName2")
            .memberNameMr("memberNameMr2")
            .villageName("villageName2")
            .villageNameMr("villageNameMr2")
            .amountMr("amountMr2")
            .savingAccNo(2L)
            .savingAccNoMr("savingAccNoMr2");
    }

    public static KarkhanaVasuliRecords getKarkhanaVasuliRecordsRandomSampleGenerator() {
        return new KarkhanaVasuliRecords()
            .id(longCount.incrementAndGet())
            .factoryMemberCode(longCount.incrementAndGet())
            .karkhanaMemberCodeMr(UUID.randomUUID().toString())
            .memberName(UUID.randomUUID().toString())
            .memberNameMr(UUID.randomUUID().toString())
            .villageName(UUID.randomUUID().toString())
            .villageNameMr(UUID.randomUUID().toString())
            .amountMr(UUID.randomUUID().toString())
            .savingAccNo(longCount.incrementAndGet())
            .savingAccNoMr(UUID.randomUUID().toString());
    }
}
