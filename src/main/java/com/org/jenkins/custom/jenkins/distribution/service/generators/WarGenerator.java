package com.org.jenkins.custom.jenkins.distribution.service.generators;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class WarGenerator {

    private static Util util = new Util();

    private static final String PACKAGER_CONFIG_YAML = "packager-config.yml";
    private static final String TEMP_PREFIX = "CDS";

    public static void generateWAR(String versionName) {
        final Config cfg;
        Path tempDirWithPrefix = null;
        try {
            tempDirWithPrefix = Files.createTempDirectory(TEMP_PREFIX);
            final File configPath = util.getFileFromResources(PACKAGER_CONFIG_YAML);
            cfg = Config.loadConfig(configPath);
            cfg.buildSettings.setTmpDir(tempDirWithPrefix.toFile());
            cfg.buildSettings.setVersion(versionName);
            cfg.buildSettings.setInstallArtifacts(true);
            new Builder(cfg).build();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            util.cleanupTempDirectory(tempDirWithPrefix.toFile());
        }
    }
}
