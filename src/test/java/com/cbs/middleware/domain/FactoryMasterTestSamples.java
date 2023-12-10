package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FactoryMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FactoryMaster getFactoryMasterSample1() {
        return new FactoryMaster()
            .id(1L)
            .factoryName("factoryName1")
            .factoryNameMr("factoryNameMr1")
            .factoryCode(1L)
            .factoryCodeMr("factoryCodeMr1")
            .factoryAddress("factoryAddress1")
            .factoryAddressMr("factoryAddressMr1")
            .description("description1");
    }

    public static FactoryMaster getFactoryMasterSample2() {
        return new FactoryMaster()
            .id(2L)
            .factoryName("factoryName2")
            .factoryNameMr("factoryNameMr2")
            .factoryCode(2L)
            .factoryCodeMr("factoryCodeMr2")
            .factoryAddress("factoryAddress2")
            .factoryAddressMr("factoryAddressMr2")
            .description("description2");
    }

    public static FactoryMaster getFactoryMasterRandomSampleGenerator() {
        return new FactoryMaster()
            .id(longCount.incrementAndGet())
            .factoryName(UUID.randomUUID().toString())
            .factoryNameMr(UUID.randomUUID().toString())
            .factoryCode(longCount.incrementAndGet())
            .factoryCodeMr(UUID.randomUUID().toString())
            .factoryAddress(UUID.randomUUID().toString())
            .factoryAddressMr(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
