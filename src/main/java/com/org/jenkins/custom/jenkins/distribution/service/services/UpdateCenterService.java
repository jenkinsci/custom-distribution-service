package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.File;
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

    private transient Util util = new Util();

    private transient final static Logger LOGGER = Logger.getLogger(UpdateCenterService.class.getName());
    private transient static final String UPDATE_CENTER_JSON_URL = "https://updates.jenkins.io/current/update-center.actual.json";
    private transient int updateFlag = 0;
    private transient String responseString;
    private transient String updateCenterFilePath = "";

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
            LOGGER.info("Executing Request at " + UPDATE_CENTER_JSON_URL);
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(new HttpGet(UPDATE_CENTER_JSON_URL))) {
                responseString = EntityUtils.toString(response.getEntity());}
            byte[] buf = responseString.getBytes();
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
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }
}
