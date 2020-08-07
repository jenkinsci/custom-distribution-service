package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private int updateFlag = 0;
    private String responseString;
    private String updateCenterFilePath = "";

    public JSONObject downloadUpdateCenterJSON() throws Exception {
        /*
        * Check if updateFlag has been set if not then it is the first time the application
        * is being run so we need to download the update-center
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
            byte[] buf = responseString.getBytes(StandardCharsets.UTF_8);
            Files.write(updateCenterFile.toPath(), buf);
            updateFlag = 1;
        } else {
            responseString = readFileAsString(updateCenterFilePath);
        }
        LOGGER.info("Returning Response");
        return util.convertPayloadToJSON(responseString);
    }

    private static String readFileAsString(String fileName) throws Exception {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        return data;
    }
}
