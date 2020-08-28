package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.generators.PackageConfigGenerator;
import com.org.jenkins.custom.jenkins.distribution.service.services.PackagerDownloadService;
import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/package")
public class PackagerController {

    private transient final PackageConfigGenerator packageConfGen;
    private transient final PackagerDownloadService packagerDownServ;
    private final static Logger LOGGER = Logger.getLogger(PackagerController.class.getName());

    @Autowired
    public PackagerController(final PackageConfigGenerator packageConfGen, final PackagerDownloadService packagerDownServ) {
        this.packageConfGen = packageConfGen;
        this.packagerDownServ = packagerDownServ;
    }

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
    public ResponseEntity<?> getPackageConfig(@RequestBody final String postPayload) {
        LOGGER.info("Request Received for packaging configuration with params" + postPayload);
        String yamlResponse = "";
        HttpStatus httpStatus;
        try {
            yamlResponse = packageConfGen.generatePackageConfig(new JSONObject(postPayload));
            httpStatus = HttpStatus.OK;
        } catch (IOException e) {
            LOGGER.severe(e.toString());
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(yamlResponse, httpStatus);
    }

    /**
     * Takes the configuration in the browsers editor and generates and returns the 
     * war file.
     * 
     * @param postPayload The content of the text editor in the browser 
     * @return a ResponseEntity instance with a body containing the generated jenkins.war package.
     */
    @PostMapping (path = "/downloadWarPackage")
    public ResponseEntity<?> downloadWAR(@RequestBody final String postPayload) {
        LOGGER.info("Request Received for downloading war file with configuration" + postPayload);
        try {
            return  packagerDownServ.downloadWAR(postPayload);
        } catch (IOException | InterruptedException e) {
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
    public ResponseEntity<?> downloadPackageConfig(@RequestBody final String postPayload) {
            LOGGER.info(postPayload);
            final InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(postPayload.getBytes(StandardCharsets.UTF_8)));
            final String headerValue = "attachment; filename=packager-config.yml";
            LOGGER.info("Returning packager-config.yml");
            return Util.returnResource(Util.returnHeaders(headerValue), postPayload.getBytes(StandardCharsets.UTF_8).length, resource);
    }

}
