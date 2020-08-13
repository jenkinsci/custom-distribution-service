package com.org.jenkins.custom.jenkins.distribution.service;

import com.palantir.docker.compose.DockerComposeRule;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;

public class DockerBuildTest {

    @ClassRule
    public static DockerComposeRule docker = DockerComposeRule.builder()
        .file("src/test/resources/docker-compose.yml")
        .build();

    @Test
    public void testDockerBuild() throws Exception {
        docker.dockerCompose().build();
    }
}
