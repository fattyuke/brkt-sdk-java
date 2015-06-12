package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a billing group.
 */
public class BillingGroupRequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public BillingGroupRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public BillingGroupRequestBuilder members(Collection<String> members) {
        reqBuilder.attr("members", members);
        return this;
    }

    public BillingGroupRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
