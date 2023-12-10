package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VillageMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static VillageMaster getVillageMasterSample1() {
        return new VillageMaster()
            .id(1L)
            .villageName("villageName1")
            .villageNameMr("villageNameMr1")
            .villageCode(1L)
            .villageCodeMr("villageCodeMr1");
    }

    public static VillageMaster getVillageMasterSample2() {
        return new VillageMaster()
            .id(2L)
            .villageName("villageName2")
            .villageNameMr("villageNameMr2")
            .villageCode(2L)
            .villageCodeMr("villageCodeMr2");
    }

    public static VillageMaster getVillageMasterRandomSampleGenerator() {
        return new VillageMaster()
            .id(longCount.incrementAndGet())
            .villageName(UUID.randomUUID().toString())
            .villageNameMr(UUID.randomUUID().toString())
            .villageCode(longCount.incrementAndGet())
            .villageCodeMr(UUID.randomUUID().toString());
    }
}
