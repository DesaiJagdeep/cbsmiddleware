package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CropMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CropMaster getCropMasterSample1() {
        return new CropMaster()
            .id(1L)
            .cropCode("cropCode1")
            .cropName("cropName1")
            .categoryCode("categoryCode1")
            .categoryName("categoryName1")
            .cropPeriod("cropPeriod1");
    }

    public static CropMaster getCropMasterSample2() {
        return new CropMaster()
            .id(2L)
            .cropCode("cropCode2")
            .cropName("cropName2")
            .categoryCode("categoryCode2")
            .categoryName("categoryName2")
            .cropPeriod("cropPeriod2");
    }

    public static CropMaster getCropMasterRandomSampleGenerator() {
        return new CropMaster()
            .id(longCount.incrementAndGet())
            .cropCode(UUID.randomUUID().toString())
            .cropName(UUID.randomUUID().toString())
            .categoryCode(UUID.randomUUID().toString())
            .categoryName(UUID.randomUUID().toString())
            .cropPeriod(UUID.randomUUID().toString());
    }
}
