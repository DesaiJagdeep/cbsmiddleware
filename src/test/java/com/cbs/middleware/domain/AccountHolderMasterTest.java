package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountHolderMasterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountHolderMaster.class);
        AccountHolderMaster accountHolderMaster1 = new AccountHolderMaster();
        accountHolderMaster1.setId(1L);
        AccountHolderMaster accountHolderMaster2 = new AccountHolderMaster();
        accountHolderMaster2.setId(accountHolderMaster1.getId());
        assertThat(accountHolderMaster1).isEqualTo(accountHolderMaster2);
        accountHolderMaster2.setId(2L);
        assertThat(accountHolderMaster1).isNotEqualTo(accountHolderMaster2);
        accountHolderMaster1.setId(null);
        assertThat(accountHolderMaster1).isNotEqualTo(accountHolderMaster2);
    }
}
