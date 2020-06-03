package com.org.jenkins.Custom.Jenkins.Distribution.Service.generators;


import com.org.jenkins.Custom.Jenkins.Distribution.Service.Util.Util;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import static com.org.jenkins.Custom.Jenkins.Distribution.Service.generators.PackageConfigGenerator.generatePackageConfig;

public class PackageConfigGeneratorTest {
    Util util = new Util();

    @Test
    public void testSimplePackageConfigGeneration() {
        try {
            String sampleConfig = util.readStringFromFile("packagerConfig/simpleConfig.json");
            String generatedYAML = generatePackageConfig(new JSONObject(sampleConfig));
            String expectedYAML = util.readStringFromFile("packagerConfig/simpleConfig.yml");
            Assert.assertEquals(generatedYAML, expectedYAML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPackagerConfigWithoutDockerTag() {
        try {
        String sampleConfig = util.readStringFromFile("packagerConfig/emptyDockerTag.json");
        String generatedYAML = generatePackageConfig(new JSONObject(sampleConfig));
        String expectedYAML = util.readStringFromFile("packagerConfig/emptyDockerTag.yaml");
        Assert.assertEquals(generatedYAML, expectedYAML);
    } catch (Exception e) {
        e.printStackTrace();
        }
    }

    @Test
    public void testPackagerConfigWithoutCasCTag() {
        try {
            String sampleConfig = util.readStringFromFile("packagerConfig/emptyCascSection.json");
            String generatedYAML = generatePackageConfig(new JSONObject(sampleConfig));
            String expectedYAML = util.readStringFromFile("packagerConfig/emptyCascSection.yaml");
            Assert.assertEquals(generatedYAML, expectedYAML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
