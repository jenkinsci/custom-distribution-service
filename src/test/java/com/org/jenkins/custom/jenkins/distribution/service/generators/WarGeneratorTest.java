package com.org.jenkins.custom.jenkins.distribution.service.generators;

import org.junit.jupiter.api.Test;

public class WarGeneratorTest {

    @Test
    public void testWarGenerationSucceeds() {
        WarGenerator.generateWAR("1.0");
    }

}
