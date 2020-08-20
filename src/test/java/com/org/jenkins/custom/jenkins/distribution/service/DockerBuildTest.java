package com.org.jenkins.custom.jenkins.distribution.service;

import java.io.File;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;

public class DockerBuildTest {

    @ClassRule
    public static DockerComposeContainer environment = new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

    @Test
    public void testContainerStart() {
        environment.start();
    }
}
