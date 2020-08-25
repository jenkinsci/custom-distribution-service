package com.org.jenkins.custom.jenkins.distribution.service.generators;


import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

public class PackageConfigGeneratorTest {
    private Util util = new Util();

    @Test
    public void testSimplePackageConfigGeneration() {
        try {
            String sampleConfig = util.readStringFromFile("packagerConfig/simpleConfig.json");
            String generatedYAML = new PackageConfigGenerator().generatePackageConfig(new JSONObject(sampleConfig));
            String expectedYAML = util.readStringFromFile("packagerConfig/simpleConfig.yml");
            Assert.assertEquals(generatedYAML, expectedYAML);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testPackagerConfigWithoutDockerTag() {
        try {
        String sampleConfig = util.readStringFromFile("packagerConfig/emptyDockerTag.json");
        String generatedYAML = new PackageConfigGenerator().generatePackageConfig(new JSONObject(sampleConfig));
        String expectedYAML = util.readStringFromFile("packagerConfig/emptyDockerTag.yaml");
        Assert.assertEquals(generatedYAML, expectedYAML);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            }
        }

    @Test
    public void testPackagerConfigWithoutCasCTag() {
        try {
            String sampleConfig = util.readStringFromFile("packagerConfig/emptyCascSection.json");
            String generatedYAML = new PackageConfigGenerator().generatePackageConfig(new JSONObject(sampleConfig));
            String expectedYAML = util.readStringFromFile("packagerConfig/emptyCascSection.yaml");
            Assert.assertEquals(generatedYAML, expectedYAML);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
