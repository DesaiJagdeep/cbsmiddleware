package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDemandKMPatrakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDemandKMPatrak.class);
        LoanDemandKMPatrak loanDemandKMPatrak1 = new LoanDemandKMPatrak();
        loanDemandKMPatrak1.setId(1L);
        LoanDemandKMPatrak loanDemandKMPatrak2 = new LoanDemandKMPatrak();
        loanDemandKMPatrak2.setId(loanDemandKMPatrak1.getId());
        assertThat(loanDemandKMPatrak1).isEqualTo(loanDemandKMPatrak2);
        loanDemandKMPatrak2.setId(2L);
        assertThat(loanDemandKMPatrak1).isNotEqualTo(loanDemandKMPatrak2);
        loanDemandKMPatrak1.setId(null);
        assertThat(loanDemandKMPatrak1).isNotEqualTo(loanDemandKMPatrak2);
    }
}
