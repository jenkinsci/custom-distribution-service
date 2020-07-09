package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
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
    /*
     * Usage : GET: package/getPackageConfiguration
     * Returns: Generates and returns Packager configuration
     */
    @PostMapping (path = "/getPackageConfiguration")
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

    /*
     * Usage : POST: package/downloadPackageConfiguration
     * Returns: Packager-config.yml
     */
    @PostMapping (path = "/downloadPackageConfiguration")
    public ResponseEntity<?> downloadPackageConfig(@RequestBody String postPayload) {
        LOGGER.info("Request Received for downloading configuration with params" + postPayload);
        File packagerConfigFile = null;
        try {
            packagerConfigFile = new File("packagerConfig.yml");
            // Write to file
            FileWriter fileWriter = new FileWriter(packagerConfigFile);
            fileWriter.write(postPayload);
            fileWriter.flush();
            fileWriter.close();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(packagerConfigFile));
            String headerValue = "attachment; filename=packager-config.yml";
            LOGGER.info("Returning packager-config.yml");
            return Util.returnResource(Util.returnHeaders(headerValue), packagerConfigFile, resource);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } finally {
            util.cleanupTempDirectory(packagerConfigFile);
        }
    }

}
