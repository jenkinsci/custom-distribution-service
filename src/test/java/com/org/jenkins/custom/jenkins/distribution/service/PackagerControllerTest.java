package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.org.jenkins.custom.jenkins.distribution.service.util.Util.returnHeaders;
import static com.org.jenkins.custom.jenkins.distribution.service.util.Util.returnResource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PackagerControllerTest extends SpringMVCSetup{

    @Override
    @Before
    public void setup() {
        super.setup();
    }

    private Util util = new Util();

    public final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset
        .forName("utf8"));

    @Test
    public void getPackageConfigTest() throws Exception {

        MockMvc packageMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String uri = "/api/package/getPackageConfiguration";
        when(packageConfigGenerator.generatePackageConfig(any(JSONObject.class))).thenReturn(util.readStringFromFile("simple-config.yaml"));
        MvcResult result = packageMvc.perform( MockMvcRequestBuilders.post(uri)
            .contentType(APPLICATION_JSON_UTF8)
            .content(util.readStringFromFile("simple-config.json"))).andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(), util.readStringFromFile("simple-config.yaml"));
        Assert.assertEquals(result.getResponse().getStatus(), 200);
    }

    @Test
    public void getPackageConfigReturns505WhenIOException() throws Exception {

        MockMvc packageMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String uri = "/api/package/getPackageConfiguration";
        when(packageConfigGenerator.generatePackageConfig(any(JSONObject.class))).thenThrow(IOException.class);
        MvcResult result = packageMvc.perform( MockMvcRequestBuilders.post(uri)
            .contentType(APPLICATION_JSON_UTF8)
            .content(util.readStringFromFile("simple-config.json"))).andReturn();
        Assert.assertEquals(500, result.getResponse().getStatus());
    }

    @Test
    public void downloadPackageConfigTest() throws Exception {
        MockMvc packageMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        String uri = "/api/package/downloadPackageConfiguration";
        MvcResult result = packageMvc.perform( MockMvcRequestBuilders.post(uri)
            .contentType(APPLICATION_JSON_UTF8)
            .content(util.readStringFromFile("simple-config.yaml"))).andReturn();
        Assert.assertEquals(result.getResponse().getContentAsString(), util.readStringFromFile("simple-config.yaml"));
        Assert.assertEquals(result.getResponse().getStatus(), 200);
    }


    @Test
    public void downloadWARReturns500WhenIOException() throws Exception {

        MockMvc packageMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        when(packagerDownloadService.downloadWAR(any(String.class))).thenThrow(IOException.class);
        String uri = "/api/package/downloadWarPackage";
        MvcResult result = packageMvc.perform( MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .content(util.readStringFromFile("simple-config.yaml"))).andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals(status, 500);
    }

    @Test
    public void downloadWarTest() throws Exception {


        MockMvc packageMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        final InputStreamResource resource = new InputStreamResource(Files.newInputStream(Paths.get(util.getFileFromResources(
            "dummy_war.txt").getAbsolutePath())));
        final String headerValue = "attachment; filename=jenkins.war";

        when(packagerDownloadService.downloadWAR(any(String.class))).
            thenReturn(returnResource(returnHeaders(headerValue), (int) util.getFileFromResources(
                "dummy_war.txt").length(), resource));
        String uri = "/api/package/downloadWarPackage";
        MvcResult result = packageMvc.perform(MockMvcRequestBuilders.post(uri)
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .content(util.readStringFromFile("simple-config.yaml"))
                                .accept(MediaType.APPLICATION_OCTET_STREAM)).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 200);
        Assert.assertNotNull(result.getResponse().getContentType().getBytes());
    }

}
