package com.cbs.middleware.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KmLoansTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static KmLoans getKmLoansSample1() {
        return new KmLoans()
            .id(1L)
            .cropName("cropName1")
            .cropNameMr("cropNameMr1")
            .loanAmountMr("loanAmountMr1")
            .areMr("areMr1")
            .receivableAmtMr("receivableAmtMr1")
            .dueAmtMr("dueAmtMr1");
    }

    public static KmLoans getKmLoansSample2() {
        return new KmLoans()
            .id(2L)
            .cropName("cropName2")
            .cropNameMr("cropNameMr2")
            .loanAmountMr("loanAmountMr2")
            .areMr("areMr2")
            .receivableAmtMr("receivableAmtMr2")
            .dueAmtMr("dueAmtMr2");
    }

    public static KmLoans getKmLoansRandomSampleGenerator() {
        return new KmLoans()
            .id(longCount.incrementAndGet())
            .cropName(UUID.randomUUID().toString())
            .cropNameMr(UUID.randomUUID().toString())
            .loanAmountMr(UUID.randomUUID().toString())
            .areMr(UUID.randomUUID().toString())
            .receivableAmtMr(UUID.randomUUID().toString())
            .dueAmtMr(UUID.randomUUID().toString());
    }
}
