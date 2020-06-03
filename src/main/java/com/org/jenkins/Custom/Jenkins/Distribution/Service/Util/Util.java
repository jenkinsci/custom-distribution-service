package com.org.jenkins.Custom.Jenkins.Distribution.Service.Util;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

public class Util {

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

}
