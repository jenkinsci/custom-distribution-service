package com.org.jenkins.custom.jenkins.distribution.service.generators;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import org.junit.jupiter.api.Test;

public class WarGeneratorTest {

    private static Util util = new Util();
    @Test
    public void testWarGenerationSucceeds() {
        WarGenerator.generateWAR("1.0");
        util.cleanupTempDirectory();
    }

}
