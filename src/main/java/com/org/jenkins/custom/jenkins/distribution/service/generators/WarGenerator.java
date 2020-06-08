package com.org.jenkins.custom.jenkins.distribution.service.generators;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;

import java.io.File;

public class WarGenerator {

    private Util util = new Util();

    public static final String DEFAULT_TMP_DIR_NAME = "tmp";

    public void generateWAR(String versionName) {
        final Config cfg;
        final File configPath = util.getFileFromResources("packager-config.yml");
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
