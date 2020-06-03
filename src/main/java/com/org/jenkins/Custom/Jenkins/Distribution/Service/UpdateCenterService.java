package com.org.jenkins.Custom.Jenkins.Distribution.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class UpdateCenterService {

    private static final String UPDATE_CENTER_JSON_URL = "https://updates.jenkins.io/current/update-center.actual.json";
    
    public void downloadUpdateCenterJSON() throws IOException {
        HttpGet get = new HttpGet(UPDATE_CENTER_JSON_URL);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get)) {
            String responseString = EntityUtils.toString(response.getEntity());
            FileUtils.writeStringToFile(new File("/src/main/resources/update-center.json"), responseString, Charset.forName("UTF-8"));
        }
    }
}
