package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankMaster.class);
        BankMaster bankMaster1 = new BankMaster();
        bankMaster1.setId(1L);
        BankMaster bankMaster2 = new BankMaster();
        bankMaster2.setId(bankMaster1.getId());
        assertThat(bankMaster1).isEqualTo(bankMaster2);
        bankMaster2.setId(2L);
        assertThat(bankMaster1).isNotEqualTo(bankMaster2);
        bankMaster1.setId(null);
        assertThat(bankMaster1).isNotEqualTo(bankMaster2);
    }
}
