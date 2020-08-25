package com.org.jenkins.custom.jenkins.distribution.service;

import com.org.jenkins.custom.jenkins.distribution.service.services.UpdateCenterService;
import java.util.Map;
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
@SuppressWarnings("PMD.AvoidCatchingGenericException")
public class PluginController {

    private transient final static Logger LOGGER = Logger.getLogger(PluginController.class.getName());
    private transient final UpdateCenterService updateService;

    @Autowired
    public PluginController(final UpdateCenterService updaterService) {
        this.updateService = updaterService;
    }

    /*
    * Usage : GET: api/plugin/getPluginList
    * Returns: Plugin List from update center in the form of a JSONObject
    */
    /**
     * Returns the plugin List from update center in the form of a JSONObject.
     * @return a ResponseEntity instance with a body containing the updateCenterJSONObject in the form
     * of a map. It is a map because the front-end requires it in that format to render the cards.
     */
    @GetMapping(path = "/getPluginList")
    public ResponseEntity<?> getPlugins() {
        LOGGER.info("Request Received");
        Map updateMap = null;
        HttpStatus status;
        try {
            updateMap = updateService.downloadUpdateCenterJSON().toMap();
            status = HttpStatus.OK;
        } catch (Exception e) {
            LOGGER.severe(e.toString());
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(updateMap, status);
    }
}
