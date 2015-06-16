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

import com.brkt.client.util.RequestBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a workload.
 */
public class WorkloadRequestBuilder extends RequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

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

    public WorkloadRequestBuilder requestedState(Constants.RequestedState state) {
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
