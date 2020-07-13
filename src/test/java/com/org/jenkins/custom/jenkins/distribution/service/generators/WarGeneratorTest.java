package com.org.jenkins.custom.jenkins.distribution.service.generators;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.fail;

public class WarGeneratorTest {

    Util util = new Util();

    @Test
    public void testWarGenerationSucceeds() {
        try {
            WarGenerator.generateWAR("jenkins-all-latest", util.readStringFromFile("packager-config.yml"));
        } catch (Exception e) {
            fail("Should not have thrown any exception" + e);
        }
    }

}
