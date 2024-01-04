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
            .caste("caste1")
            .casteMr("casteMr1")
            .pacsNumber("pacsNumber1")
            .areaHect("areaHect1")
            .aadharNo(1L)
            .aadharNoMr("aadharNoMr1")
            .panNo(1L)
            .panNoMr("panNoMr1")
            .mobileNo("mobileNo1")
            .mobileNoMr("mobileNoMr1")
            .kccNo("kccNo1")
            .kccNoMr("kccNoMr1")
            .savingNo("savingNo1")
            .savingNoMr("savingNoMr1")
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
            .caste("caste2")
            .casteMr("casteMr2")
            .pacsNumber("pacsNumber2")
            .areaHect("areaHect2")
            .aadharNo(2L)
            .aadharNoMr("aadharNoMr2")
            .panNo(2L)
            .panNoMr("panNoMr2")
            .mobileNo("mobileNo2")
            .mobileNoMr("mobileNoMr2")
            .kccNo("kccNo2")
            .kccNoMr("kccNoMr2")
            .savingNo("savingNo2")
            .savingNoMr("savingNoMr2")
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
            .caste(UUID.randomUUID().toString())
            .casteMr(UUID.randomUUID().toString())
            .pacsNumber(UUID.randomUUID().toString())
            .areaHect(UUID.randomUUID().toString())
            .aadharNo(longCount.incrementAndGet())
            .aadharNoMr(UUID.randomUUID().toString())
            .panNo(longCount.incrementAndGet())
            .panNoMr(UUID.randomUUID().toString())
            .mobileNo(UUID.randomUUID().toString())
            .mobileNoMr(UUID.randomUUID().toString())
            .kccNo(UUID.randomUUID().toString())
            .kccNoMr(UUID.randomUUID().toString())
            .savingNo(UUID.randomUUID().toString())
            .savingNoMr(UUID.randomUUID().toString())
            .entryFlag(UUID.randomUUID().toString());
    }
}
