package com.org.jenkins.custom.jenkins.distribution.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PageControllerTest {
    @Autowired
	private MockMvc mockMvc;

    @Test
    public void redirectsInternally() throws Exception {
        this.mockMvc.perform(get("/pluginList")).andDo(print())
                .andExpect(status().is(200))
                .andExpect(view().name("forward:/index.html"));
    }

}