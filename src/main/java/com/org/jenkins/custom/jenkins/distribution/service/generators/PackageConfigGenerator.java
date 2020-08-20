package com.org.jenkins.custom.jenkins.distribution.service.generators;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.org.jenkins.custom.jenkins.distribution.service.services.UpdateCenterService;
import java.io.IOException;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PackageConfigGenerator {

    private final static UpdateCenterService UPDATE_SERVICE = new UpdateCenterService();

    public final static String generatePackageConfig(final JSONObject formData)
        throws IOException {

        //Get the bundle object
        final JSONObject bundle = formData.getJSONObject("bundle");
        //Building the initial bundle
        final JSONObject bundleInfo = generateBundle(bundle);

        //Get the build settings object
        final JSONObject buildSettings = formData.getJSONObject("buildSettings");
        final JSONObject docker = generateBuildSettings(buildSettings);

        //Get the war object
        final JSONObject war = formData.getJSONObject("war");
        //Building the WAR File
        final JSONObject warInfo = generateWarSettings(war);

        //Building system Settings
        final JSONObject systemSettings = formData.getJSONObject("sysSettings");
        final JSONObject sysInfo = generateSystemSettings(systemSettings);

        final JSONArray pluginArray = formData.getJSONArray("plugins");
        final JSONArray pluginInfoArray = generatePluginList(pluginArray);

        final JSONObject packageConfig = new JSONObject();
        packageConfig.put("buildSettings", docker);
        packageConfig.put("bundle", bundleInfo);
        packageConfig.put("war", warInfo);
        packageConfig.put("plugins", pluginInfoArray);
        packageConfig.put("systemProperties", sysInfo);

        if(formData.getBoolean("casc")) {
            packageConfig.put("casc", generateJCasC());
        }

        // parse JSON
        final JsonNode jsonNodeTree = new ObjectMapper().readTree(packageConfig.toString());
        return new YAMLMapper().writeValueAsString(jsonNodeTree);
    }

    private static JSONObject generateBundle(final JSONObject bundle) {
        final JSONObject bundleInfo = new JSONObject();
        bundleInfo.put("groupId", "io.jenkins.tools.custom-distribution-service.build");
        bundleInfo.put("artifactId", bundle.getString("artifactId"));
        bundleInfo.put("vendor", "Jenkins project");
        bundleInfo.put("title", bundle.getString("title"));
        bundleInfo.put("description", bundle.getString("description"));
        return bundleInfo;
    }

    private static JSONObject generateBuildSettings(final JSONObject buildSettings) {
        final JSONObject dockerInfo = new JSONObject();
        dockerInfo.put("base", buildSettings.getString("base"));
        if (buildSettings.has("tag")) {
            dockerInfo.put("tag", buildSettings.getString("tag"));
            dockerInfo.put("build", "true");
        }

        return new JSONObject().put("docker", dockerInfo);
    }

    private static JSONObject generateWarSettings(final JSONObject warSettings) {
        final JSONObject warInfo = new JSONObject();
        warInfo.put("groupId", "org.jenkins-ci.main");
        warInfo.put("artifactId", "jenkins-war");
        warInfo.put("source", new JSONObject().put("version", warSettings.getString("jenkinsVersion")));
        return warInfo;
    }

    private static JSONObject generateSystemSettings(final JSONObject systemSettings) {
        final JSONObject sysInfo = new JSONObject();
        sysInfo.put("jenkins.install.runSetupWizard", systemSettings.getString("setupWizard"));
        sysInfo.put("jenkins.model.Jenkins.slaveAgentPort",
            systemSettings.getString("slaveAgentPort"));
        sysInfo.put("jenkins.model.Jenkins.slaveAgentPortEnforce",
            systemSettings.getString("slaveAgentPortEnforce"));
        return sysInfo;
    }

    private static JSONArray generatePluginList(final JSONArray pluginArray) throws IOException {

        final JSONArray pluginInfoArray = new JSONArray();
        for (int i = 0; i < pluginArray.length(); i++) {
            final JSONObject pluginObject = pluginArray.getJSONObject(i);
            final Iterator<String> pluginNames = pluginObject.keys();
            while (pluginNames.hasNext()) {
                final JSONObject pluginInfo = new JSONObject();
                final String pluginName = pluginNames.next();
                pluginInfo.put("groupId", "org.jenkins-ci.plugins");
                pluginInfo.put("artifactId", pluginName);
                pluginInfo.put("source", new JSONObject().put("version", UPDATE_SERVICE.downloadUpdateCenterJSON()
                                                                         .getJSONObject("plugins")
                                                                         .getJSONObject(pluginName)
                                                                         .getString("version")));
                pluginInfoArray.put(pluginInfo);
            }
        }
        return pluginInfoArray;
    }

    private static JSONObject generateJCasC() {
        final JSONObject jcascInfo = new JSONObject();
        jcascInfo.put("id", "casc");
        jcascInfo.put("source", new JSONObject().put("dir", "casc.yml"));
        return jcascInfo;
    }

}
