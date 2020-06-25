package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.services.UpdateCenterService;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("api/plugin")
public class PluginController {

    private final static Logger LOGGER = Logger.getLogger(PluginController.class.getName());
    private final UpdateCenterService updateCenterService;

    @Autowired
    public PluginController(UpdateCenterService updateCenterService) {
        this.updateCenterService = updateCenterService;
    }

    /*
    * Usage : GET: api/plugin/getPluginList
    * Returns: Plugin List from update center in the form of a JSONObject
    */
    @GetMapping(path = "/getPluginList")
    public ResponseEntity<?> getPlugins() {
        LOGGER.info("Request Received");
        try {
            return new ResponseEntity<>(updateCenterService.downloadUpdateCenterJSON().toMap(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
