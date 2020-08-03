package com.org.jenkins.custom.jenkins.distribution.service.services;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class UpdateCenterServiceTest {

    @Test
    public void checkIfUpdateCenterJSONOIsNotNull() {
        try {
            UpdateCenterService updateCenterService = new UpdateCenterService();
            Assert.assertNotNull(updateCenterService.downloadUpdateCenterJSON());
        } catch (Exception e) {
            fail();
        }
    }
}
