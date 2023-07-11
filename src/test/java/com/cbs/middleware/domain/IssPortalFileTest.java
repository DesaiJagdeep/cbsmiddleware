package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssPortalFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssPortalFile.class);
        IssPortalFile issPortalFile1 = new IssPortalFile();
        issPortalFile1.setId(1L);
        IssPortalFile issPortalFile2 = new IssPortalFile();
        issPortalFile2.setId(issPortalFile1.getId());
        assertThat(issPortalFile1).isEqualTo(issPortalFile2);
        issPortalFile2.setId(2L);
        assertThat(issPortalFile1).isNotEqualTo(issPortalFile2);
        issPortalFile1.setId(null);
        assertThat(issPortalFile1).isNotEqualTo(issPortalFile2);
    }
}
