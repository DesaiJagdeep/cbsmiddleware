package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CbsDataReportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CbsDataReport.class);
        CbsDataReport cbsDataReport1 = new CbsDataReport();
        cbsDataReport1.setId(1L);
        CbsDataReport cbsDataReport2 = new CbsDataReport();
        cbsDataReport2.setId(cbsDataReport1.getId());
        assertThat(cbsDataReport1).isEqualTo(cbsDataReport2);
        cbsDataReport2.setId(2L);
        assertThat(cbsDataReport1).isNotEqualTo(cbsDataReport2);
        cbsDataReport1.setId(null);
        assertThat(cbsDataReport1).isNotEqualTo(cbsDataReport2);
    }
}
