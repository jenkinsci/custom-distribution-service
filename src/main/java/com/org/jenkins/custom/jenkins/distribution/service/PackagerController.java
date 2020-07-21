package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.org.jenkins.custom.jenkins.distribution.service.generators.PackageConfigGenerator.generatePackageConfig;

@RestController
@CrossOrigin
@RequestMapping("/package")

public class PackagerController {

    private Util util = new Util();
    private final static Logger LOGGER = Logger.getLogger(PackagerController.class.getName());

    /**
     * Generate and return the package YAML configuration string.
     *
     * The internal JSON object is composed interactively in the browser by the user.
     * This JSON object contains the list of plugins and the core war file details.
     * The JSON object is converted to a YAML string.
     * This string is returned in the body of the ResponseEntity,
     * then it is loaded in the text editor in the browser.
     *
     * @param postPayload The internal JSON object.
     * @return a ResponseEntity instance with a body containing the package configuration as a YAML string.
     */
    @PostMapping(path = "/getPackageConfiguration")
    public ResponseEntity<?> getPackageConfig(@RequestBody String postPayload) {
        LOGGER.info("Request Received for packaging configuration with params" + postPayload);
        try {
            String yamlResponse = generatePackageConfig(new JSONObject(postPayload));
            return new ResponseEntity<>(yamlResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Send the content of the browser's text editor to the client (a download).
     *
     * @param postPayload The content of the text editor in the browser.
     * @return The content of the browser's text editor in an HTTP response (download from the server).
     */
    @PostMapping (path = "/downloadPackageConfiguration")
    public ResponseEntity<?> downloadPackageConfig(@RequestBody String postPayload) {
        try {
            LOGGER.info(postPayload);
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(postPayload.getBytes()));
            String headerValue = "attachment; filename=packager-config.yml";
            LOGGER.info("Returning packager-config.yml");
            return Util.returnResource(Util.returnHeaders(headerValue), postPayload.getBytes().length, resource);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
