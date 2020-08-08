package com.org.jenkins.custom.jenkins.distribution.service.util;

import java.io.File;
import java.io.IOException;
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

@SuppressWarnings("PMD.ShortClassName")
public class Util {

    private final static Logger LOGGER = Logger.getLogger(Util.class.getName());

    public File getFileFromResources(final String fileName) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    public String readStringFromFile(final String filename) throws IOException {
        return new String(Files.readAllBytes(getFileFromResources(filename).toPath()));
    }

    public void cleanupTempDirectory(final File file) {
        // Cleanup the temporary directory
        try {
            final File tmpDir = file;
            if (tmpDir.exists()) {
                LOGGER.info("deleting temporary directory: " + file.getAbsolutePath());
                FileUtils.deleteDirectory(tmpDir);
            }
        }catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public JSONObject convertPayloadToJSON(final String payload) throws IOException{
        return new JSONObject(payload);
    }

    public static ResponseEntity<Resource> returnResource(final HttpHeaders headers,final  int fileLength, final InputStreamResource resource){
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(fileLength)
            .contentType(MediaType.parseMediaType( "application/octet-stream"))
            .body(resource);
    }

    public static HttpHeaders returnHeaders(final String headerValue) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }
}
