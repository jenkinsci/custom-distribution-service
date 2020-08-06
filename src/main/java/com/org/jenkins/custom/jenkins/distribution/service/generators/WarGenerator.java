package com.org.jenkins.custom.jenkins.distribution.service.generators;

import io.jenkins.tools.warpackager.lib.config.Config;
import io.jenkins.tools.warpackager.lib.impl.Builder;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

@SuppressWarnings("PMD.LawOfDemeter")
public class WarGenerator {

    private final static Logger LOGGER = Logger.getLogger(WarGenerator.class.getName());
    private static final String TEMP_PREFIX = "CDS";


    public static File generateWAR(final String versionName, final String configuration) throws Exception {
        LOGGER.info("Generating War File");
        final Config cfg;
        final Path tempDirWithPrefix = Files.createTempDirectory(TEMP_PREFIX);
            final File packagerFile = File.createTempFile("packager-config", ".yml");
            final byte[] buf = configuration.getBytes();
            Files.write(packagerFile.toPath(), buf);
            cfg = Config.loadConfig(packagerFile);
            cfg.buildSettings.setTmpDir(tempDirWithPrefix.toFile());
            cfg.buildSettings.setVersion(versionName);
            cfg.buildSettings.setInstallArtifacts(true);
            new Builder(cfg).build();
            LOGGER.info("Cleaning up temporary directory");
            packagerFile.deleteOnExit();
            return cfg.getOutputWar();
    }

}
