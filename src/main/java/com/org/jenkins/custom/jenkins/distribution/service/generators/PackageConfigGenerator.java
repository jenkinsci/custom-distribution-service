package com.org.jenkins.custom.jenkins.distribution.service.generators;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.org.jenkins.custom.jenkins.distribution.service.services.UpdateCenterService;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PackageConfigGenerator {

    private final static UpdateCenterService updateCenterService = new UpdateCenterService();

    public static String generatePackageConfig(JSONObject formData)
        throws  Exception {

        //Get the bundle object
        JSONObject bundle = formData.getJSONObject("bundle");
        //Building the initial bundle
        JSONObject bundleInfo = generateBundle(bundle);

        //Get the build settings object
        JSONObject buildSettings = formData.getJSONObject("buildSettings");
        JSONObject docker = generateBuildSettings(buildSettings);

        //Get the war object
        JSONObject war = formData.getJSONObject("war");
        //Building the WAR File
        JSONObject warInfo = generateWarSettings(war);

        //Building system Settings
        JSONObject systemSettings = formData.getJSONObject("sysSettings");
        JSONObject sysInfo = generateSystemSettings(systemSettings);

        JSONArray pluginArray = formData.getJSONArray("plugins");
        JSONArray pluginInfoArray = generatePluginList(pluginArray);

        JSONObject packageConfig = new JSONObject();
        packageConfig.put("buildSettings", docker);
        packageConfig.put("bundle", bundleInfo);
        packageConfig.put("war", warInfo);
        packageConfig.put("plugins", pluginInfoArray);
        packageConfig.put("systemProperties", sysInfo);

        if(formData.getBoolean("casc")) {
            packageConfig.put("casc", generateJCasC());
        }

        // parse JSON
        JsonNode jsonNodeTree = new ObjectMapper().readTree(packageConfig.toString());
        // save it as YAML
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        return jsonAsYaml;
    }

    private static JSONObject generateBundle(JSONObject bundle) {
        JSONObject bundleInfo = new JSONObject();
        bundleInfo.put("groupId", "io.jenkins.tools.custom-distribution-service.build");
        bundleInfo.put("artifactId", bundle.getString("artifactId"));
        bundleInfo.put("vendor", "Jenkins project");
        bundleInfo.put("title", bundle.getString("title"));
        bundleInfo.put("description", bundle.getString("description"));
        return bundleInfo;
    }

    private static JSONObject generateBuildSettings(JSONObject buildSettings) {
        JSONObject dockerInfo = new JSONObject();
        dockerInfo.put("base", buildSettings.getString("base"));
        if (buildSettings.has("tag")) {
            dockerInfo.put("tag", buildSettings.getString("tag"));
            dockerInfo.put("build", "true");
        }
        JSONObject docker = new JSONObject().put("docker", dockerInfo);
        return docker;
    }

    private static JSONObject generateWarSettings(JSONObject warSettings) {
        JSONObject warInfo = new JSONObject();
        warInfo.put("groupId", "org.jenkins-ci.main");
        warInfo.put("artifactId", "jenkins-war");
        warInfo.put("source", new JSONObject().put("version", warSettings.getString("jenkinsVersion")));
        return warInfo;
    }

    private static JSONObject generateSystemSettings(JSONObject systemSettings) {
        JSONObject sysInfo = new JSONObject();
        sysInfo.put("jenkins.install.runSetupWizard", systemSettings.getString("setupWizard"));
        sysInfo.put("jenkins.model.Jenkins.slaveAgentPort",
            systemSettings.getString("slaveAgentPort"));
        sysInfo.put("jenkins.model.Jenkins.slaveAgentPortEnforce",
            systemSettings.getString("slaveAgentPortEnforce"));
        return sysInfo;
    }

    private static JSONArray generatePluginList(JSONArray pluginArray) throws Exception {

        JSONArray pluginInfoArray = new JSONArray();
        for (int i = 0; i < pluginArray.length(); i++) {
            JSONObject pluginObject = pluginArray.getJSONObject(i);
            Iterator<String> pluginNames = pluginObject.keys();
            while (pluginNames.hasNext()) {
                JSONObject pluginInfo = new JSONObject();
                String pluginName = pluginNames.next();
                pluginInfo.put("groupId", "org.jenkins-ci.plugins");
                pluginInfo.put("artifactId", pluginName);
                pluginInfo.put("source", new JSONObject().put("version", updateCenterService.downloadUpdateCenterJSON()
                                                                         .getJSONObject("plugins")
                                                                         .getJSONObject(pluginName)
                                                                         .getString("version")));
                pluginInfoArray.put(pluginInfo);
            }
        }
        return pluginInfoArray;
    }

    private static JSONObject generateJCasC() {
        JSONObject jcascInfo = new JSONObject();
        jcascInfo.put("id", "casc");
        jcascInfo.put("source", new JSONObject().put("dir", "casc.yml"));
        return jcascInfo;
    }

}
