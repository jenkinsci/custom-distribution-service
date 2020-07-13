package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class PullRequestService {

    private static Util util = new Util();
    private final static Logger LOGGER = Logger.getLogger(PullRequestService.class.getName());


    public String createPullRequest(String branch, String prDesc) throws Exception {

        JSONObject pullRequestDetails = new JSONObject();
        pullRequestDetails.put("title", "New Packager Configuration");
        pullRequestDetails.put("body", prDesc);
        pullRequestDetails.put("head", branch);
        pullRequestDetails.put("base", "master");

        String url = "https://api.github.com/repos/jenkinsci/custom-distribution-service/pulls";
        HttpPost post = new HttpPost(url);
        post.addHeader("Accept", "application/vnd.github.sailor-v-preview+json");
        post.addHeader("Authorization", returnToken());

        StringEntity input = new StringEntity(pullRequestDetails.toString());
        input.setContentType("application/json");
        post.setEntity(input);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post)) {
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject requestJSON = util.convertPayloadToJSON(responseString);
            return requestJSON.getString("html_url");
        }
    }

    /*This function will be called to kick off the creation of the pull request*/
    public String createBranch(String branchName, String prDesc) throws Exception {
        JSONObject branchDetails = new JSONObject();
            branchDetails.put("ref", "refs/heads/" + branchName);
            branchDetails.put("sha", getMasterSHA());
            String url = "https://api.github.com/repos/jenkinsci/custom-distribution-service/git/refs";

            HttpPost post = new HttpPost(url);
            post.addHeader("Accept", "application/vnd.github.sailor-v-preview+json");
            post.addHeader("Authorization", returnToken());
            StringEntity input = new StringEntity(branchDetails.toString());
            input.setContentType("application/json");
            post.setEntity(input);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {
                String responseString = EntityUtils.toString(response.getEntity());
                LOGGER.info("Posting commit response: " + responseString);
            }

        return createFile("packager-config.yml", branchName, prDesc);
    }

    public String createFile(String filename, String branchName, String prDesc) throws Exception {
            String url = "https://api.github.com/repos/jenkinsci/custom-distribution-service/contents/" + filename;

            /*This string for he packager config has to be passed using a file */
            String packageContent = readFile("", StandardCharsets.US_ASCII);
            String base64encodedString = Base64.getEncoder().encodeToString(packageContent.getBytes("utf-8"));
            JSONObject newFileDetails = new JSONObject();
            newFileDetails.put("message", "Adding a new file");
            newFileDetails.put("content", base64encodedString);
            newFileDetails.put("branch", branchName);
            newFileDetails.put("sha", getMasterSHA());

            HttpPut put = new HttpPut(url);
            put.addHeader("Accept", "application/vnd.github.sailor-v-preview+json");
            put.addHeader("Authorization", returnToken());

            StringEntity input = new StringEntity(newFileDetails.toString());
            input.setContentType("application/json");
            put.setEntity(input);

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(put)) {
            }
            return createPullRequest(branchName, prDesc);
        }

    public String getMasterSHA() throws Exception {
            String url = "https://api.github.com/repos/jenkinsci/custom-distribution-service/git/ref/heads/master";
            HttpGet get = new HttpGet(url);
            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {
                String responseString = EntityUtils.toString(response.getEntity());
                JSONObject requestJSON = util.convertPayloadToJSON(responseString);
                return requestJSON.getJSONObject("object").getString("sha");
            }
        }

    private String returnToken() throws Exception{
        String token = null;

        /*Got to find a way to read the token, that is generated at the time of authenticating the application*/
        token = readFile("", StandardCharsets.US_ASCII);
        String finaltoken = "token " + token;
        return finaltoken;
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
