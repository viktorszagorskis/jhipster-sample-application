package org.vizadev.ddcapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.vizadev.ddcapp.web.rest.TestUtil;

public class BriefingHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BriefingHistory.class);
        BriefingHistory briefingHistory1 = new BriefingHistory();
        briefingHistory1.setId(1L);
        BriefingHistory briefingHistory2 = new BriefingHistory();
        briefingHistory2.setId(briefingHistory1.getId());
        assertThat(briefingHistory1).isEqualTo(briefingHistory2);
        briefingHistory2.setId(2L);
        assertThat(briefingHistory1).isNotEqualTo(briefingHistory2);
        briefingHistory1.setId(null);
        assertThat(briefingHistory1).isNotEqualTo(briefingHistory2);
    }
}
