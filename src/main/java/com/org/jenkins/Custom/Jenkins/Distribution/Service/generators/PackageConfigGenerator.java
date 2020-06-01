package com.org.jenkins.Custom.Jenkins.Distribution.Service.generators;
import org.json.JSONException;
import org.json.JSONObject;

public class PackageConfigGenerator {


    public static String generatePackageConfig(JSONObject formData) throws JSONException {

        //Get the bundle object
        JSONObject bundle = formData.getJSONObject("bundle");
        //Building the initial bundle
        JSONObject bundleInfo = new JSONObject();
        bundleInfo.put("groupId", "io.jenkins.tools.custom-distribution-service.build");
        bundleInfo.put("artifactId", bundle.getString("artifactId"));
        bundleInfo.put("vendor", "Jenkins project");
        bundleInfo.put("title", bundle.getString("title"));
        bundleInfo.put("description", bundle.getString("desc"));

        //Get the build settings object
        JSONObject buildSettings = formData.getJSONObject("buildSettings");
        JSONObject dockerInfo = new JSONObject();
        dockerInfo.put("base", buildSettings.getString("base"));
        if(buildSettings.has("tag")) {
            dockerInfo.put("tag", buildSettings.getString("tag"));
            dockerInfo.put("build", "true");
        }
        JSONObject docker = new JSONObject().put("docker", dockerInfo);

        //Get the war object
        JSONObject war = formData.getJSONObject("war");
        //Building the WAR File
        JSONObject warInfo = new JSONObject();
        warInfo.put("groupId", "org.jenkins-ci.main");
        warInfo.put("artifactId", "jenkins-war");
        warInfo.put("source", new JSONObject().put("version", war.getString("jenkinsVersion")));

        JSONObject packageConfig = new JSONObject();
        packageConfig.put("buildSettings", docker);
        packageConfig.put("bundle", bundleInfo);
        packageConfig.put("war", warInfo);

        /*
        * TO-DO
        * a) Add Plugin List
        * */

        return packageConfig.toString();
    }

}
