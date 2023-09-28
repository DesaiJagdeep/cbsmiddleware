package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KMPUploadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KMPUpload.class);
        KMPUpload kMPUpload1 = new KMPUpload();
        kMPUpload1.setId(1L);
        KMPUpload kMPUpload2 = new KMPUpload();
        kMPUpload2.setId(kMPUpload1.getId());
        assertThat(kMPUpload1).isEqualTo(kMPUpload2);
        kMPUpload2.setId(2L);
        assertThat(kMPUpload1).isNotEqualTo(kMPUpload2);
        kMPUpload1.setId(null);
        assertThat(kMPUpload1).isNotEqualTo(kMPUpload2);
    }
}
