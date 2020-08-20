package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.generators.PackageConfigGenerator;
import com.org.jenkins.custom.jenkins.distribution.service.generators.WarGenerator;
import com.org.jenkins.custom.jenkins.distribution.service.services.PackagerDownloadService;
import com.org.jenkins.custom.jenkins.distribution.service.services.UpdateCenterService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomJenkinsDistributionServiceApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public abstract class SpringMVCSetup {

    protected MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    public UpdateCenterService updateService;

    @MockBean
    public PackageConfigGenerator packageConfigGenerator;

    @MockBean
    public WarGenerator warGenerator;

    @MockBean
    public PackagerDownloadService packagerDownloadService;

    protected void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
}