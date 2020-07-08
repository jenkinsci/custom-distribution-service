package com.org.jenkins.custom.jenkins.distribution.service.generators;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class WarGenerator {

    private final static Logger LOGGER = Logger.getLogger(WarGenerator.class.getName());
    private static Util util = new Util();
    private static final String TEMP_PREFIX = "CDS";

    public static void generateWAR(String versionName, String configuration) {
        LOGGER.info("Generating War File");
        final Config cfg;
        Path tempDirWithPrefix = null;
        try {
            tempDirWithPrefix = Files.createTempDirectory(TEMP_PREFIX);
            File packagerConfigFile = File.createTempFile("packager-config", ".yml");
            byte[] buf = configuration.getBytes();
            Files.write(packagerConfigFile.toPath(), buf);
            cfg = Config.loadConfig(packagerConfigFile);
            cfg.buildSettings.setTmpDir(tempDirWithPrefix.toFile());
            cfg.buildSettings.setVersion(versionName);
            cfg.buildSettings.setInstallArtifacts(true);
            new Builder(cfg).build();
            LOGGER.info("Cleaning up temporary directory");
            packagerConfigFile.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            util.cleanupTempDirectory(tempDirWithPrefix.toFile());
        }
    }
}
