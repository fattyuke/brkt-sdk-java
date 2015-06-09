package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a billing group.
 */
public class BillingGroupRequestBuilder extends RequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    private BillingGroupRequestBuilder(boolean forCreate) {
        if (forCreate) {
            reqBuilder.requiredFields("name");
        }
    }

    /**
     * Build a request for creating a billing group.
     */
    public static BillingGroupRequestBuilder newCreateRequest() {
        return new BillingGroupRequestBuilder(true);
    }

    /**
     * Build a request for updating a billing group.
     */
    public static BillingGroupRequestBuilder newUpdateRequest() {
        return new BillingGroupRequestBuilder(false);
    }

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
     *
     * @throws com.brkt.client.util.RequestBuilder.MissingFieldsError
     * if any required fields are missing
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
