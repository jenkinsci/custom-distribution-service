package com.org.jenkins.custom.jenkins.distribution.service.services;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.org.jenkins.custom.jenkins.distribution.service.util.Util;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateCenterServiceTest {

    /**
     * A rule which provides a mock server.
     */
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    private Util util = new Util();

    @Test
    public void checkUpdateCenterResponse() {
        try {
            // Get update center mock body
            String updateCenterBody = util.readStringFromFile("updateCenter.json");
            wireMockRule.stubFor(get(urlPathMatching("/getUpdateCenter"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(updateCenterBody)));
            JSONObject updateCenterJSON = new UpdateCenterService().downloadJSON("http://localhost:8080/getUpdateCenter");
            // Check if the returned JSON is not null
            assertNotNull(updateCenterJSON);
            Assert.assertEquals(updateCenterJSON.toString(), updateCenterBody);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
