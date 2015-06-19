/*
* Copyright 2015 Bracket Computing, Inc. All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License").
* You may not use this file except in compliance with the License.
* A copy of the License is located at
*
* https://github.com/brkt/brkt-sdk-java/blob/master/LICENSE
*
* or in the "license" file accompanying this file. This file is
* distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
* CONDITIONS OF ANY KIND, either express or implied. See the 
* License for the specific language governing permissions and
* limitations under the License.
*/

package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a CloudInit.
 */
public class CloudInitRequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public CloudInitRequestBuilder cloudConfig(String cloudConfig) {
        reqBuilder.attr("cloud_config", cloudConfig);
        return this;
    }

    public CloudInitRequestBuilder deploymentType(Constants.DeploymentType type) {
        reqBuilder.attr("deployment_type", type);
        return this;
    }

    public CloudInitRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public CloudInitRequestBuilder metadata(Map<String, String> metadata) {
        reqBuilder.attr("metadata", metadata);
        return this;
    }

    public CloudInitRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    public CloudInitRequestBuilder userData(String userData) {
        reqBuilder.attr("user_data", userData);
        return this;
    }

    public CloudInitRequestBuilder userScript(String userScript) {
        reqBuilder.attr("user_script", userScript);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
