package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a CloudInit.
 */
public class CloudInitRequestBuilder extends RequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    private CloudInitRequestBuilder(boolean forCreate) {
        if (forCreate) {
            reqBuilder.requiredFields("deployment_type", "name");
        }
    }

    /**
     * Build a request for creating a CloudInit.
     */
    public static CloudInitRequestBuilder newCreateRequest() {
        return new CloudInitRequestBuilder(true);
    }

    /**
     * Build a request for updating a CloudInit.
     */
    public static CloudInitRequestBuilder newUpdateRequest() {
        return new CloudInitRequestBuilder(false);
    }

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
     *
     * @throws com.brkt.client.util.RequestBuilder.MissingFieldsError
     * if any required fields are missing
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
