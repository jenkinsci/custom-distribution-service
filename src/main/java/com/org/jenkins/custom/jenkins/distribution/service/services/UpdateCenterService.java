package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UpdateCenterService {

    private Util util = new Util();

    private final static Logger LOGGER = Logger.getLogger(UpdateCenterService.class.getName());
    private static final String UPDATE_CENTER_JSON_URL = "https://updates.jenkins.io/current/update-center.actual.json";
    
    public JSONObject downloadUpdateCenterJSON() throws Exception {
        HttpGet get = new HttpGet(UPDATE_CENTER_JSON_URL);
        LOGGER.info("Executing Request");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);
        String responseString = EntityUtils.toString(response.getEntity());
        /*
        * Instead of writing to a file it would be better to return a json object as of now
        * and later once we have the caching mechanism in place we could pull it in from there
        */

        LOGGER.info("Update flag is " + updateFlag);
        LOGGER.info("Update Center Path " + updateCenterFilePath);

        if(updateFlag == 0) {
            File updateCenterFile = File.createTempFile("update-center", ".json");
            updateCenterFilePath = updateCenterFile.getPath();
            LOGGER.info("Creating a new file" + updateCenterFile.getPath());
            HttpGet get = new HttpGet(UPDATE_CENTER_JSON_URL);
            LOGGER.info("Executing Request at " + UPDATE_CENTER_JSON_URL);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);
            responseString = EntityUtils.toString(response.getEntity());
            byte[] buf = responseString.getBytes();
            Files.write(updateCenterFile.toPath(), buf);
            updateFlag = 1;
        } else {
            responseString = readFileAsString(updateCenterFilePath);
        }
        LOGGER.info("Returning Response");
        return util.convertPayloadToJSON(responseString);
    }
}
