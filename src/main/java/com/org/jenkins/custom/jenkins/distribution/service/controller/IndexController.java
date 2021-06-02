package com.org.jenkins.custom.jenkins.distribution.service.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/error")
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    public String error() {
        return "This is the backend of the custom distribution service application. If you see this message the service is up and running";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}