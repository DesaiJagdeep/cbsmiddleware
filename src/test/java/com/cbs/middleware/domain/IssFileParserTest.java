package com.cbs.middleware.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.cbs.middleware.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssFileParserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssFileParser.class);
        IssFileParser issFileParser1 = new IssFileParser();
        issFileParser1.setId(1L);
        IssFileParser issFileParser2 = new IssFileParser();
        issFileParser2.setId(issFileParser1.getId());
        assertThat(issFileParser1).isEqualTo(issFileParser2);
        issFileParser2.setId(2L);
        assertThat(issFileParser1).isNotEqualTo(issFileParser2);
        issFileParser1.setId(null);
        assertThat(issFileParser1).isNotEqualTo(issFileParser2);
    }
}
