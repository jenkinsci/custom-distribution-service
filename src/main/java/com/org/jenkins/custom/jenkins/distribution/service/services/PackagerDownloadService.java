package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.generators.WarGenerator;
import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PackagerDownloadService {

    private final static Logger LOGGER = Logger.getLogger(PackagerDownloadService.class.getName());
    private static Util util = new Util();

    /**
     * @param configuration This is the configuration with which the war is generated.
     * @return Response Entity with body as the war file in the form of a resource
     * @throws Exception
     */
    public ResponseEntity<Resource> downloadWAR(final String configuration) throws IOException, InterruptedException {
        File warFile = null;
        try {
            LOGGER.info("We are about to enter the war generator function");
            warFile = WarGenerator.generateWAR(configuration);
            final InputStreamResource resource = new InputStreamResource(Files.newInputStream(Paths.get(warFile.getAbsolutePath())));
            final String headerValue = "attachment; filename=jenkins.war";
            LOGGER.info("Returning War file");
            return returnResource(returnHeaders(headerValue), warFile, resource);
        } finally {
            if(warFile != null) {
                util.cleanupTempDirectory(warFile);
            }
        }
    }

    private ResponseEntity<Resource> returnResource(final HttpHeaders headers, final File file, final InputStreamResource resource){
        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType( "application/octet-stream"))
            .body(resource);
    }

    private HttpHeaders returnHeaders(final String headerValue) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }

}

