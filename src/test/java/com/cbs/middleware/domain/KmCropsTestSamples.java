package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KmCropsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KmCrops getKmCropsSample1() {
        return new KmCrops()
            .id(1L)
            .cropName("cropName1")
            .cropNameMr("cropNameMr1")
            .hectorMr("hectorMr1")
            .areMr("areMr1")
            .previousAmtMr("previousAmtMr1")
            .demandMr("demandMr1")
            .society("society1")
            .societyMr("societyMr1")
            .bankAmtMr("bankAmtMr1")
            .noOfTreeMr("noOfTreeMr1");
    }

    public static KmCrops getKmCropsSample2() {
        return new KmCrops()
            .id(2L)
            .cropName("cropName2")
            .cropNameMr("cropNameMr2")
            .hectorMr("hectorMr2")
            .areMr("areMr2")
            .previousAmtMr("previousAmtMr2")
            .demandMr("demandMr2")
            .society("society2")
            .societyMr("societyMr2")
            .bankAmtMr("bankAmtMr2")
            .noOfTreeMr("noOfTreeMr2");
    }

    public static KmCrops getKmCropsRandomSampleGenerator() {
        return new KmCrops()
            .id(longCount.incrementAndGet())
            .cropName(UUID.randomUUID().toString())
            .cropNameMr(UUID.randomUUID().toString())
            .hectorMr(UUID.randomUUID().toString())
            .areMr(UUID.randomUUID().toString())
            .previousAmtMr(UUID.randomUUID().toString())
            .demandMr(UUID.randomUUID().toString())
            .society(UUID.randomUUID().toString())
            .societyMr(UUID.randomUUID().toString())
            .bankAmtMr(UUID.randomUUID().toString())
            .noOfTreeMr(UUID.randomUUID().toString());
    }
}
