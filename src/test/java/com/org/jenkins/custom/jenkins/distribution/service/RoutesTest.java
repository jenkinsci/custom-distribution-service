package com.org.jenkins.custom.jenkins.distribution.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class RoutesTest  extends SpringMVCSetup {
    @Test
    public void all_the_routes() throws Exception {
        List<String> routes = webApplicationContext
                .getBean(RequestMappingHandlerMapping.class)
                .getHandlerMethods()
                .keySet()
                .stream()
                .map(entry -> entry.toString())
                .sorted()
                .collect(Collectors.toList());
        
        Assert.assertEquals(routes, Arrays.asList(
                "{GET /**/{path:[^\\.]*}}",
                "{GET /api/plugin/getPluginList}",
                "{POST /api/package/downloadPackageConfiguration}",
                "{POST /api/package/downloadWarPackage}",
                "{POST /api/package/getPackageConfiguration}"
        ));
    }
}
