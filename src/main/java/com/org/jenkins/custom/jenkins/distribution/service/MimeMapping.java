package com.org.jenkins.custom.jenkins.distribution.service;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MimeMapping implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(final ConfigurableServletWebServerFactory factory) {
        final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        factory.setMimeMappings(mappings);
    }
}