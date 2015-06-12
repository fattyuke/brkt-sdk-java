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
