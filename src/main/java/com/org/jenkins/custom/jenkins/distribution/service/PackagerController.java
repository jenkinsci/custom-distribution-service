package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.services.PackagerDownloadService;
import java.util.logging.Logger;
import org.json.JSONObject;
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

    private final static Logger LOGGER = Logger.getLogger(PackagerController.class.getName());
    /*
     * Usage : GET: api/plugin/getPluginList
     * Returns: Plugin List from update center in the form of a JSONObject
     */
    @PostMapping (path = "/getPackageConfiguration")
    public ResponseEntity<?> getPackageConfig(@RequestBody String postPayload) {
        LOGGER.info("Request Receive0d for packaging configuration with params" + postPayload);
        try {
            String yamlResponse = generatePackageConfig(new JSONObject(postPayload));
            return new ResponseEntity<>(yamlResponse, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping (path = "/downloadWarPackage")
    public ResponseEntity<?> downloadPackageConfig(@RequestBody String postPayload) {
        LOGGER.info("Request Received for downloading war file with configuration" + postPayload);
        try {
            return new PackagerDownloadService().downloadWAR("0.1", postPayload);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
