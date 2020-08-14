package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.nio.charset.Charset;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



public class PackageControllerTest extends SpringMVCSetup{

    private Util util = new Util();

    public final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset
        .forName("utf8"));

    @Test
    public void return_package_configuration() throws Exception {

        // Setup Mock
        String uri = "/package/getPackageConfiguration";
        mvc.perform( MockMvcRequestBuilders.post(uri)
            .contentType(APPLICATION_JSON_UTF8)
            .content(util.readStringFromFile("simple-config.json")));
    }
}
