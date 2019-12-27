package org.vizadev.ddcapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.vizadev.ddcapp.web.rest.TestUtil;

public class BriefingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Briefing.class);
        Briefing briefing1 = new Briefing();
        briefing1.setId(1L);
        Briefing briefing2 = new Briefing();
        briefing2.setId(briefing1.getId());
        assertThat(briefing1).isEqualTo(briefing2);
        briefing2.setId(2L);
        assertThat(briefing1).isNotEqualTo(briefing2);
        briefing1.setId(null);
        assertThat(briefing1).isNotEqualTo(briefing2);
    }
}
