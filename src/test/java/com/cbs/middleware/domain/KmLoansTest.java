package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KmLoansTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KmLoans.class);
        KmLoans kmLoans1 = new KmLoans();
        kmLoans1.setId(1L);
        KmLoans kmLoans2 = new KmLoans();
        kmLoans2.setId(kmLoans1.getId());
        assertThat(kmLoans1).isEqualTo(kmLoans2);
        kmLoans2.setId(2L);
        assertThat(kmLoans1).isNotEqualTo(kmLoans2);
        kmLoans1.setId(null);
        assertThat(kmLoans1).isNotEqualTo(kmLoans2);
    }
}
