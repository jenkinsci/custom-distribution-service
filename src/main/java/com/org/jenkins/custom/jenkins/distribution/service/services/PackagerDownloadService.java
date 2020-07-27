package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.generators.WarGenerator;
import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

@Service
public class PackagerDownloadService {

    private final static Logger LOGGER = Logger.getLogger(PackagerDownloadService.class.getName());
    private static Util util = new Util();


    /**
     * @param versionName This is the version name of the war file eg: jenkins-all-latest
     * @param configuration This is the configuration with which the war is generated.
     * @return Response Entity with body as the war file in the form of a resource
     * @throws Exception
     */
    public ResponseEntity<Resource> downloadWAR(String versionName, String configuration) throws Exception {
        File warFile = null;
        try {
            warFile = WarGenerator.generateWAR(versionName, configuration);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(warFile));
            String headerValue = "attachment; filename=jenkins.war";
            LOGGER.info("Returning War file");
            return returnResource(returnHeaders(headerValue), warFile, resource);
        } catch (Exception e) {
            throw e;
        } finally {
            if(warFile != null)
            util.cleanupTempDirectory(warFile);
        }
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
        return yamlMaps.get("bundle").get("artifactId");
    }

}

