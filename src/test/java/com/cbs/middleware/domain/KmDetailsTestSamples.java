package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KmDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KmDetails getKmDetailsSample1() {
        return new KmDetails()
            .id(1L)
            .sharesMr("sharesMr1")
            .sugarSharesMr("sugarSharesMr1")
            .depositMr("depositMr1")
            .dueLoanMr("dueLoanMr1")
            .dueAmountMr("dueAmountMr1")
            .bagayatHectorMr("bagayatHectorMr1")
            .bagayatAreMr("bagayatAreMr1")
            .jirayatHectorMr("jirayatHectorMr1")
            .jirayatAreMr("jirayatAreMr1")
            .landValueMr("landValueMr1")
            .eAgreementAmt("eAgreementAmt1")
            .bojaAmountMr("bojaAmountMr1");
    }

    public static KmDetails getKmDetailsSample2() {
        return new KmDetails()
            .id(2L)
            .sharesMr("sharesMr2")
            .sugarSharesMr("sugarSharesMr2")
            .depositMr("depositMr2")
            .dueLoanMr("dueLoanMr2")
            .dueAmountMr("dueAmountMr2")
            .bagayatHectorMr("bagayatHectorMr2")
            .bagayatAreMr("bagayatAreMr2")
            .jirayatHectorMr("jirayatHectorMr2")
            .jirayatAreMr("jirayatAreMr2")
            .landValueMr("landValueMr2")
            .eAgreementAmt("eAgreementAmt2")
            .bojaAmountMr("bojaAmountMr2");
    }

    public static KmDetails getKmDetailsRandomSampleGenerator() {
        return new KmDetails()
            .id(longCount.incrementAndGet())
            .sharesMr(UUID.randomUUID().toString())
            .sugarSharesMr(UUID.randomUUID().toString())
            .depositMr(UUID.randomUUID().toString())
            .dueLoanMr(UUID.randomUUID().toString())
            .dueAmountMr(UUID.randomUUID().toString())
            .bagayatHectorMr(UUID.randomUUID().toString())
            .bagayatAreMr(UUID.randomUUID().toString())
            .jirayatHectorMr(UUID.randomUUID().toString())
            .jirayatAreMr(UUID.randomUUID().toString())
            .landValueMr(UUID.randomUUID().toString())
            .eAgreementAmt(UUID.randomUUID().toString())
            .bojaAmountMr(UUID.randomUUID().toString());
    }
}
