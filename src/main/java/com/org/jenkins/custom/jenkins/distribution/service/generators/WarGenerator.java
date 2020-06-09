package com.org.jenkins.custom.jenkins.distribution.service.generators;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;

import java.io.File;

public class WarGenerator {

    private static Util util = new Util();

    private static final String DEFAULT_TMP_DIR_NAME = "tmp";
    private static final String PACKAGER_CONFIG_YAML = "packager-config.yml";

    public static void generateWAR(String versionName) {
        final Config cfg;
        final File configPath = util.getFileFromResources(PACKAGER_CONFIG_YAML);
        try {
            cfg = Config.loadConfig(configPath);
            cfg.buildSettings.setTmpDir(new File(DEFAULT_TMP_DIR_NAME));
            cfg.buildSettings.setVersion(versionName);
            cfg.buildSettings.setInstallArtifacts(true);
            new Builder(cfg).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
