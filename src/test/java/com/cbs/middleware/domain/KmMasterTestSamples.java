package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KmMasterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KmMaster getKmMasterSample1() {
        return new KmMaster()
            .id(1L)
            .branchCode("branchCode1")
            .farmerName("farmerName1")
            .farmerNameMr("farmerNameMr1")
            .farmerAddress("farmerAddress1")
            .farmerAddressMr("farmerAddressMr1")
            .gender("gender1")
            .cast("cast1")
            .castMr("castMr1")
            .pacsNumber("pacsNumber1")
            .areaHector("areaHector1")
            .aadharNo(1L)
            .aadharNoMr("aadharNoMr1")
            .panNo(1L)
            .panNoMr("panNoMr1")
            .mobileNo("mobileNo1")
            .mobileNoMr("mobileNoMr1")
            .savingNo(1L)
            .savingNoMr("savingNoMr1")
            .pacsMemberCode("pacsMemberCode1")
            .entryFlag("entryFlag1");
    }

    public static KmMaster getKmMasterSample2() {
        return new KmMaster()
            .id(2L)
            .branchCode("branchCode2")
            .farmerName("farmerName2")
            .farmerNameMr("farmerNameMr2")
            .farmerAddress("farmerAddress2")
            .farmerAddressMr("farmerAddressMr2")
            .gender("gender2")
            .cast("cast2")
            .castMr("castMr2")
            .pacsNumber("pacsNumber2")
            .areaHector("areaHector2")
            .aadharNo(2L)
            .aadharNoMr("aadharNoMr2")
            .panNo(2L)
            .panNoMr("panNoMr2")
            .mobileNo("mobileNo2")
            .mobileNoMr("mobileNoMr2")
            .savingNo(2L)
            .savingNoMr("savingNoMr2")
            .pacsMemberCode("pacsMemberCode2")
            .entryFlag("entryFlag2");
    }

    public static KmMaster getKmMasterRandomSampleGenerator() {
        return new KmMaster()
            .id(longCount.incrementAndGet())
            .branchCode(UUID.randomUUID().toString())
            .farmerName(UUID.randomUUID().toString())
            .farmerNameMr(UUID.randomUUID().toString())
            .farmerAddress(UUID.randomUUID().toString())
            .farmerAddressMr(UUID.randomUUID().toString())
            .gender(UUID.randomUUID().toString())
            .cast(UUID.randomUUID().toString())
            .castMr(UUID.randomUUID().toString())
            .pacsNumber(UUID.randomUUID().toString())
            .areaHector(UUID.randomUUID().toString())
            .aadharNo(longCount.incrementAndGet())
            .aadharNoMr(UUID.randomUUID().toString())
            .panNo(longCount.incrementAndGet())
            .panNoMr(UUID.randomUUID().toString())
            .mobileNo(UUID.randomUUID().toString())
            .mobileNoMr(UUID.randomUUID().toString())
            .savingNo(longCount.incrementAndGet())
            .savingNoMr(UUID.randomUUID().toString())
            .pacsMemberCode(UUID.randomUUID().toString())
            .entryFlag(UUID.randomUUID().toString());
    }
}
