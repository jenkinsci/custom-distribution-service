package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.nio.charset.Charset;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


public class PackageControllerTest extends SpringMVCSetup{

    @Override
    @Before
    public void setup() {
        super.setup();
    }

    private Util util = new Util();

    public final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset
        .forName("utf8"));

    @Test
    public void generate_packager_configYAML() throws Exception {

        String uri = "/package/getPackageConfiguration";
        MvcResult result = mvc.perform( MockMvcRequestBuilders.post(uri)
            .contentType(APPLICATION_JSON_UTF8)
            .content(util.readStringFromFile("simple-config.json"))).andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(), util.readStringFromFile("simple-config.yaml"));
    }

    @Test
    public void download_packager_configYAML() throws Exception {
        String uri = "/package/downloadPackageConfiguration";
        MvcResult result = mvc.perform( MockMvcRequestBuilders.post(uri)
            .contentType(APPLICATION_JSON_UTF8)
            .content(util.readStringFromFile("simple-config.yaml"))).andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(), util.readStringFromFile("simple-config.yaml"));
    }

    @Test
    public void fail_warDownload_due_to_casc_section() {
        String uri = "/package/downloadWarPackage";
        try {
             mvc.perform( MockMvcRequestBuilders.post(uri)
                .contentType(APPLICATION_JSON_UTF8)
                .content(util.readStringFromFile("simple-config.yaml"))).andReturn();
        } catch ( Exception e) {
            Assert.fail();
        }
    }
}
