package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KarkhanaVasuliFileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KarkhanaVasuliFile getKarkhanaVasuliFileSample1() {
        return new KarkhanaVasuliFile()
            .id(1L)
            .fileName("fileName1")
            .uniqueFileName("uniqueFileName1")
            .address("address1")
            .addressMr("addressMr1")
            .hangam("hangam1")
            .hangamMr("hangamMr1")
            .factoryName("factoryName1")
            .factoryNameMr("factoryNameMr1")
            .totalAmountMr("totalAmountMr1")
            .branchCode(1L)
            .pacsName("pacsName1");
    }

    public static KarkhanaVasuliFile getKarkhanaVasuliFileSample2() {
        return new KarkhanaVasuliFile()
            .id(2L)
            .fileName("fileName2")
            .uniqueFileName("uniqueFileName2")
            .address("address2")
            .addressMr("addressMr2")
            .hangam("hangam2")
            .hangamMr("hangamMr2")
            .factoryName("factoryName2")
            .factoryNameMr("factoryNameMr2")
            .totalAmountMr("totalAmountMr2")
            .branchCode(2L)
            .pacsName("pacsName2");
    }

    public static KarkhanaVasuliFile getKarkhanaVasuliFileRandomSampleGenerator() {
        return new KarkhanaVasuliFile()
            .id(longCount.incrementAndGet())
            .fileName(UUID.randomUUID().toString())
            .uniqueFileName(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .addressMr(UUID.randomUUID().toString())
            .hangam(UUID.randomUUID().toString())
            .hangamMr(UUID.randomUUID().toString())
            .factoryName(UUID.randomUUID().toString())
            .factoryNameMr(UUID.randomUUID().toString())
            .totalAmountMr(UUID.randomUUID().toString())
            .branchCode(longCount.incrementAndGet())
            .pacsName(UUID.randomUUID().toString());
    }
}
