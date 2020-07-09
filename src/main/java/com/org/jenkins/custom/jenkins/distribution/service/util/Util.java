package com.org.jenkins.custom.jenkins.distribution.service.util;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Logger;
import org.codehaus.plexus.util.FileUtils;
import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class Util {

    private final static Logger LOGGER = Logger.getLogger(Util.class.getName());

    public File getFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    public String readStringFromFile(String filename) throws Exception {
        return new String(Files.readAllBytes(getFileFromResources(filename).toPath()));
    }

    public void cleanupTempDirectory(File file) {
        // Cleanup the temporary directory
        try {
            final File tmpDir = file;
            if (tmpDir.exists()) {
                LOGGER.info("deleting temporary directory: " + file.getAbsolutePath());
                FileUtils.deleteDirectory(tmpDir);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONObject convertPayloadToJSON(String payload) throws Exception{
        return new JSONObject(payload);
    }

    public static ResponseEntity<Resource> returnResource(HttpHeaders headers, File file, InputStreamResource resource){
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType( "application/octet-stream"))
            .body(resource);
    }

    public static HttpHeaders returnHeaders(String headerValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }
}
