package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BatchTransactionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BatchTransaction.class);
        BatchTransaction batchTransaction1 = new BatchTransaction();
        batchTransaction1.setId(1L);
        BatchTransaction batchTransaction2 = new BatchTransaction();
        batchTransaction2.setId(batchTransaction1.getId());
        assertThat(batchTransaction1).isEqualTo(batchTransaction2);
        batchTransaction2.setId(2L);
        assertThat(batchTransaction1).isNotEqualTo(batchTransaction2);
        batchTransaction1.setId(null);
        assertThat(batchTransaction1).isNotEqualTo(batchTransaction2);
    }
}
