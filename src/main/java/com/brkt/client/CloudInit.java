package com.brkt.client;

import java.util.Map;

public class CloudInit extends BrktResource {
    private String cloudConfig;
    private Constants.DeploymentType deploymentType;
    private Map<String, String> metadata;
    private String userData;
    private String userScript;

    public String getCloudConfig() {
        return cloudConfig;
    }

    public Constants.DeploymentType getDeploymentType() {
        return deploymentType;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getUserData() {
        return userData;
    }

    public String getUserScript() {
        return userScript;
    }
}
