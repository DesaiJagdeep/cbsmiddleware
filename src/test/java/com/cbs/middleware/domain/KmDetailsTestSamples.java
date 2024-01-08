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
            .depositeMr("depositeMr1")
            .dueLoanMr("dueLoanMr1")
            .dueAmountMr("dueAmountMr1")
            .dueDateMr("dueDateMr1")
            .kmDateMr("kmDateMr1")
            .kmFromDateMr("kmFromDateMr1")
            .kmToDateMr("kmToDateMr1")
            .bagayatHectorMr("bagayatHectorMr1")
            .bagayatAreMr("bagayatAreMr1")
            .jirayatHectorMr("jirayatHectorMr1")
            .jirayatAreMr("jirayatAreMr1")
            .zindagiNo(1L)
            .surveyNo(1L)
            .landValueMr("landValueMr1")
            .eAgreementAmtMr("eAgreementAmtMr1")
            .eAgreementDateMr("eAgreementDateMr1")
            .bojaAmountMr("bojaAmountMr1")
            .bojaDateMr("bojaDateMr1");
    }

    public static KmDetails getKmDetailsSample2() {
        return new KmDetails()
            .id(2L)
            .sharesMr("sharesMr2")
            .sugarSharesMr("sugarSharesMr2")
            .depositeMr("depositeMr2")
            .dueLoanMr("dueLoanMr2")
            .dueAmountMr("dueAmountMr2")
            .dueDateMr("dueDateMr2")
            .kmDateMr("kmDateMr2")
            .kmFromDateMr("kmFromDateMr2")
            .kmToDateMr("kmToDateMr2")
            .bagayatHectorMr("bagayatHectorMr2")
            .bagayatAreMr("bagayatAreMr2")
            .jirayatHectorMr("jirayatHectorMr2")
            .jirayatAreMr("jirayatAreMr2")
            .zindagiNo(2L)
            .surveyNo(2L)
            .landValueMr("landValueMr2")
            .eAgreementAmtMr("eAgreementAmtMr2")
            .eAgreementDateMr("eAgreementDateMr2")
            .bojaAmountMr("bojaAmountMr2")
            .bojaDateMr("bojaDateMr2");
    }

    public static KmDetails getKmDetailsRandomSampleGenerator() {
        return new KmDetails()
            .id(longCount.incrementAndGet())
            .sharesMr(UUID.randomUUID().toString())
            .sugarSharesMr(UUID.randomUUID().toString())
            .depositeMr(UUID.randomUUID().toString())
            .dueLoanMr(UUID.randomUUID().toString())
            .dueAmountMr(UUID.randomUUID().toString())
            .dueDateMr(UUID.randomUUID().toString())
            .kmDateMr(UUID.randomUUID().toString())
            .kmFromDateMr(UUID.randomUUID().toString())
            .kmToDateMr(UUID.randomUUID().toString())
            .bagayatHectorMr(UUID.randomUUID().toString())
            .bagayatAreMr(UUID.randomUUID().toString())
            .jirayatHectorMr(UUID.randomUUID().toString())
            .jirayatAreMr(UUID.randomUUID().toString())
            .zindagiNo(longCount.incrementAndGet())
            .surveyNo(longCount.incrementAndGet())
            .landValueMr(UUID.randomUUID().toString())
            .eAgreementAmtMr(UUID.randomUUID().toString())
            .eAgreementDateMr(UUID.randomUUID().toString())
            .bojaAmountMr(UUID.randomUUID().toString())
            .bojaDateMr(UUID.randomUUID().toString());
    }
}
