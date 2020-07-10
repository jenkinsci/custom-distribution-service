package com.org.jenkins.custom.jenkins.distribution.service.generators;

import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class WarGenerator {

    private final static Logger LOGGER = Logger.getLogger(WarGenerator.class.getName());
    private static final String TEMP_PREFIX = "CDS";


    public static File generateWAR(String versionName, String configuration) throws Exception {
        LOGGER.info("Generating War File");
        final Config cfg;
        Path tempDirWithPrefix = Files.createTempDirectory(TEMP_PREFIX);
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
            return cfg.getOutputWar();
    }

}
