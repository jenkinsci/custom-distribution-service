package com.org.jenkins.custom.jenkins.distribution.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class PluginControllerTest extends SpringMVCSetup {

    @Override
    @Before
    public void setup() {
        super.setup();
    }

    @Test
    public void getPluginList() throws Exception {
        String uri = "/api/plugin/getPluginList";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}
