package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a security group.
 */
public class SecurityGroupRequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

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
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
