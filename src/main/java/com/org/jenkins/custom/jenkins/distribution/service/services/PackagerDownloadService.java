package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class PackagerDownloadService {


    Util util = new Util();
    public ResponseEntity<Resource> downloadWAR(String versionName) throws Exception {
        //Call WarGenerator function
        String artifactId = getArtifactId();
        File warFile = new File("/tmp/output/target/" + artifactId + "-" + versionName + ".war");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(warFile));
        String headerValue = "attachment; filename=jenkins.war";
        System.out.println("Returning War file");
        return returnResource(returnHeaders(headerValue), warFile, resource);
    }

    private ResponseEntity<Resource> returnResource(HttpHeaders headers, File file, InputStreamResource resource){
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType( "application/octet-stream"))
            .body(resource);
    }

    private HttpHeaders returnHeaders(String headerValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }


    private String getArtifactId() throws Exception {
        Yaml yaml = new Yaml();
        Map<String , Map<String,String>> yamlMaps = (Map<String, Map<String,String>>) yaml.load(util.readStringFromFile("packager-config.yml"));
        String artifactId = yamlMaps.get("bundle").get("artifactId");
        return artifactId;
    }
}

