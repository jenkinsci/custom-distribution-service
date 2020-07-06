package com.org.jenkins.custom.jenkins.distribution.service;

import java.util.List;
import org.kohsuke.github.GHApp;
import org.kohsuke.github.GHAppInstallation;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import static com.org.jenkins.custom.jenkins.distribution.service.GithubJwtHelper.createJWT;

public class GithubController {

    private static String APP_IDENTIFIER="";

    @SuppressWarnings("deprecation")
    private static String authenticateApplication() {
        try {
            String jwtToken = createJWT(APP_IDENTIFIER, 600000);
            GitHub gitHubApp = new GitHubBuilder().withEndpoint("https://api.github.com").withJwtToken(jwtToken).build();
            GHApp app = gitHubApp.getApp();
            List<GHAppInstallation> appInstallations = app.listInstallations().asList();
            if (!appInstallations.isEmpty()) {
                GHAppInstallation appInstallation = appInstallations.get(0);
                GHAppInstallationToken appInstallationToken = appInstallation
                    .createToken(appInstallation.getPermissions())
                    .create();
                return appInstallationToken.getToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
