package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankBranchMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankBranchMaster.class);
        BankBranchMaster bankBranchMaster1 = new BankBranchMaster();
        bankBranchMaster1.setId(1L);
        BankBranchMaster bankBranchMaster2 = new BankBranchMaster();
        bankBranchMaster2.setId(bankBranchMaster1.getId());
        assertThat(bankBranchMaster1).isEqualTo(bankBranchMaster2);
        bankBranchMaster2.setId(2L);
        assertThat(bankBranchMaster1).isNotEqualTo(bankBranchMaster2);
        bankBranchMaster1.setId(null);
        assertThat(bankBranchMaster1).isNotEqualTo(bankBranchMaster2);
    }
}
