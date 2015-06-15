/*
* Copyright 2015 Bracket Computing, Inc. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://github.com/brkt/brkt-java-sdk/blob/master/LICENSE
*
* or in the "license" file accompanying this file. This file is
* distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
* CONDITIONS OF ANY KIND, either express or implied. See the 
* License for the specific language governing permissions and
* limitations under the License.
*/

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
