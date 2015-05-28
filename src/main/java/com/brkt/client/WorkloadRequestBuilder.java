package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

public class WorkloadRequestBuilder extends RequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    private WorkloadRequestBuilder(boolean forCreate) {
        if (forCreate) {
            reqBuilder.requiredFields("billing_group", "name", "zone");
        }
    }

    /**
     * Build a request for creating a workload.
     */
    public static WorkloadRequestBuilder newCreateRequest() {
        return new WorkloadRequestBuilder(true);
    }

    /**
     * Build a request for updating a workload.
     */
    public static WorkloadRequestBuilder newUpdateRequest() {
        return new WorkloadRequestBuilder(false);
    }

    public WorkloadRequestBuilder billingGroupId(String id) {
        reqBuilder.attr("billing_group", id);
        return this;
    }

    public WorkloadRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    public WorkloadRequestBuilder zoneId(String id) {
        reqBuilder.attr("zone", id);
        return this;
    }

    public WorkloadRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public WorkloadRequestBuilder enableServiceDomain(boolean b) {
        reqBuilder.attr("enable_service_domain", b);
        return this;
    }

    public WorkloadRequestBuilder id(String id) {
        reqBuilder.attr("id", id);
        return this;
    }

    public WorkloadRequestBuilder leaseExpireTime(Timestamp t) {
        reqBuilder.attr("lease_expire_time", t);
        return this;
    }

    public WorkloadRequestBuilder maxCost(BigDecimal d) {
        reqBuilder.attr("max_cost", d);
        return this;
    }

    public WorkloadRequestBuilder metadata(Map<String, String> metadata) {
        reqBuilder.attr("metadata", metadata);
        return this;
    }

    public WorkloadRequestBuilder requestedState(String state) {
        reqBuilder.attr("requested_state", state);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     *
     * @throws com.brkt.client.util.RequestBuilder.MissingFieldsError
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
