package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a security group.
 */
public class SecurityGroupRequestBuilder extends RequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    private SecurityGroupRequestBuilder(boolean forCreate) {
        if (forCreate) {
            reqBuilder.requiredFields("customer", "name");
        }
    }

    /**
     * Build a request for creating a security group.
     */
    public static SecurityGroupRequestBuilder newCreateRequest() {
        return new SecurityGroupRequestBuilder(true);
    }

    /**
     * Build a request for updating a security group.
     */
    public static SecurityGroupRequestBuilder newUpdateRequest() {
        return new SecurityGroupRequestBuilder(false);
    }

    public SecurityGroupRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public SecurityGroupRequestBuilder metadata(Map<String, String> metadata) {
        reqBuilder.attr("metadata", metadata);
        return this;
    }

    public SecurityGroupRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    public SecurityGroupRequestBuilder requestedState(Constants.RequestedState state) {
        reqBuilder.attr("requested_state", state);
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
