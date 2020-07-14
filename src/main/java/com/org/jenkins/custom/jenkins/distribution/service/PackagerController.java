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
     * @param postPayload  This is the JSON Object with all of the plugin configuration and war file details
     * @return Generates and returns Packager configuration based on the JSON Object supplied as postPayload
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
     * @param postPayload Configuration on the front end that gets posted as a string
     * @return Downloads the package configuration i.e the configuration that the user types on the editor gets pulled in and downloaded
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
