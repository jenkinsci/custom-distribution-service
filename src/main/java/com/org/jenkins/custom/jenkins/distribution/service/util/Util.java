package com.org.jenkins.custom.jenkins.distribution.service.util;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Logger;
import org.codehaus.plexus.util.FileUtils;

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

}
