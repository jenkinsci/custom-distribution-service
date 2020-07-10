package com.org.jenkins.custom.jenkins.distribution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.jenkins.custom.jenkins.distribution.service.services.PackagerDownloadService;
import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import static com.org.jenkins.custom.jenkins.distribution.service.generators.PackageConfigGenerator.generatePackageConfig;
@RestController
@CrossOrigin
@RequestMapping("/package")

public class PackagerController {

    private static Util util = new Util();

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

    /**
     * @param postPayload Configuration for the war file
     * @return War file
     */
    @PostMapping (path = "/downloadWarPackage")
    public ResponseEntity<?> downloadWAR(@RequestBody String postPayload) {
        LOGGER.info("Request Received for downloading war file with configuration");
        try {
            return new PackagerDownloadService().downloadWAR(getWarVersion(), postPayload);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    private String getWarVersion() throws Exception {
        Yaml yaml = new Yaml();
        Map<String , Map<String,String>> yamlMaps = (Map<String, Map<String,String>>) yaml.load(util.readStringFromFile("packager-config.yml"));
        JSONObject json = new JSONObject(new ObjectMapper().writeValueAsString(yamlMaps.get("war")));
        return json.getJSONObject("source").get("version").toString();
    }
}
