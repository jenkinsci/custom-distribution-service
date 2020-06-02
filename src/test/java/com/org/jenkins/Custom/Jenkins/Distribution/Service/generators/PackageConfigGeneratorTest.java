package com.org.jenkins.Custom.Jenkins.Distribution.Service.generators;


import com.org.jenkins.Custom.Jenkins.Distribution.Service.Util.Util;
import java.nio.file.Files;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import static com.org.jenkins.Custom.Jenkins.Distribution.Service.generators.PackageConfigGenerator.generatePackageConfig;

public class PackageConfigGeneratorTest {
    Util util = new Util();

    @Test
    public void testPackageConfigGeneration() {

        try {
            String sampleConfig = new String(Files.readAllBytes(util.getFileFromResources("packagerConfig/simpleConfig.json").toPath()));
            String generatedYAML = generatePackageConfig(new JSONObject(sampleConfig));
            String expectedYAML = new String(Files.readAllBytes(util.getFileFromResources("packagerConfig/simpleConfig.yml").toPath()));
            Assert.assertEquals(generatedYAML, expectedYAML);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
