package com.cbs.middleware.domain;

import static com.cbs.middleware.domain.KmDetailsTestSamples.*;
import static com.cbs.middleware.domain.KmLoansTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmLoansTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmLoans.class);
        KmLoans kmLoans1 = getKmLoansSample1();
        KmLoans kmLoans2 = new KmLoans();
        assertThat(kmLoans1).isNotEqualTo(kmLoans2);

        kmLoans2.setId(kmLoans1.getId());
        assertThat(kmLoans1).isEqualTo(kmLoans2);

        kmLoans2 = getKmLoansSample2();
        assertThat(kmLoans1).isNotEqualTo(kmLoans2);
    }

    @Test
    void kmDetailsTest() throws Exception {
        KmLoans kmLoans = getKmLoansRandomSampleGenerator();
        KmDetails kmDetailsBack = getKmDetailsRandomSampleGenerator();

        kmLoans.setKmDetails(kmDetailsBack);
        assertThat(kmLoans.getKmDetails()).isEqualTo(kmDetailsBack);

        kmLoans.kmDetails(null);
        assertThat(kmLoans.getKmDetails()).isNull();
    }
}
