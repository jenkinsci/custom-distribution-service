package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.IOException;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PluginControllerTest extends SpringMVCSetup {

    private Util util = new Util();

    @Override
    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void return_status_200() throws Exception {

        // Setup Mock
        String dummyUpdateBody = util.readStringFromFile("updateCenter.json");
        when(updateService.downloadUpdateCenterJSON()).thenReturn(util.convertPayloadToJSON(dummyUpdateBody));

        String uri = "/api/plugin/getPluginList";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertThat(new JSONObject(dummyUpdateBody).toString(), CoreMatchers.containsString(mvcResult.getResponse().getContentAsString()));
        assertEquals(200, status);
    }

    @Test
    public void return_status_404() throws Exception {
        // Mock the update Center service class to throw an Exception
        when(updateService.downloadUpdateCenterJSON()).thenThrow(IOException.class);
        String uri = "/api/plugin/getPluginList";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }
}
