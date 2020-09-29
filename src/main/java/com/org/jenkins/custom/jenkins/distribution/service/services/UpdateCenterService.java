package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.File;
import java.io.IOException;
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

    private final transient Util util = new Util();

    private transient final static Logger LOGGER = Logger.getLogger(UpdateCenterService.class.getName());
    private transient static final String UPDATE_CENTER_URL = "https://updates.jenkins.io/current/update-center.actual.json";
    private transient int updateFlag;
    private transient String responseString;
    private transient String updateCenterPath = "";

    public JSONObject downloadUpdateCenterJSON() throws IOException {
        /*
        * Check if updateFlag has been set if not then it is the first time the application
        * is being run so we need to download the update-center
        */
        LOGGER.info("Update flag is " + updateFlag);
        LOGGER.info("Update Center Path " + updateCenterPath);
        if(updateFlag == 0) {
            downloadJSON(UPDATE_CENTER_URL);
            updateFlag = 1;
        } else {
            responseString = readFileAsString(updateCenterPath);
        }
        LOGGER.info("Returning Response Successfully");
        return util.convertPayloadToJSON(responseString);
    }

   public JSONObject downloadJSON (final String updateCenterURL) throws IOException {
            final File updateCenterFile = File.createTempFile("update-center", ".json");
            updateCenterPath = updateCenterFile.getPath();
            LOGGER.info("Creating a new file '" + updateCenterFile.getPath() + "'");
            LOGGER.info("Executing Request at '" + updateCenterURL + "'");
            try (
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    CloseableHttpResponse response = httpClient.execute(new HttpGet(UPDATE_CENTER_URL))
            ) {
               responseString = EntityUtils.toString(response.getEntity());
            }
            final byte[] buf = responseString.getBytes(StandardCharsets.UTF_8);
            Files.write(updateCenterFile.toPath(), buf);
            LOGGER.info("Returning Response from API");
            // Mark the file for deletion once the JVM shuts down
            updateCenterFile.deleteOnExit();
            return util.convertPayloadToJSON(responseString);
   }      

    private static String readFileAsString(final String fileName) throws IOException {
        String data;
        data = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
        return data;
    }
}
