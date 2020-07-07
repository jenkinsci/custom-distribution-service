package com.org.jenkins.custom.jenkins.distribution.service;

import java.util.List;
import org.kohsuke.github.GHApp;
import org.kohsuke.github.GHAppInstallation;
import org.kohsuke.github.GHAppInstallationToken;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.org.jenkins.custom.jenkins.distribution.service.GithubJwtHelper.createJWT;
import static com.org.jenkins.custom.jenkins.distribution.service.GithubJwtHelper.get;


@RestController
@CrossOrigin("*")
public class GithubController {

    @Value("${APP_IDENTIFIER}")
    private String getAppIdentifier;

    @PostMapping(path = "/event_handler")
    public void handleEvent(@RequestBody String requestBodyString) throws Exception {
        System.out.println("Received event handler event");
        String token = authenticateApplication();
        System.out.println(token);
    }

    @SuppressWarnings("deprecation")
    private String authenticateApplication() {
        try {
            String APP_IDENTIFIER = getAppIdentifier.replaceAll("^\"+|\"+$", "");
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
