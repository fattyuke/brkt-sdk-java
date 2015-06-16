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

import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a load balancer.
 */
public class LoadBalancerRequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public LoadBalancerRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public LoadBalancerRequestBuilder healthCheckInterval(int seconds) {
        reqBuilder.attr("health_check_interval", seconds);
        return this;
    }

    public LoadBalancerRequestBuilder healthCheckTarget(String path) {
        reqBuilder.attr("health_check_target", path);
        return this;
    }

    public LoadBalancerRequestBuilder healthCheckTimeout(int seconds) {
        reqBuilder.attr("health_check_timeout", seconds);
        return this;
    }

    public LoadBalancerRequestBuilder healthyThreshold(int numTimes) {
        reqBuilder.attr("healthy_threshold", numTimes);
        return this;
    }

    public LoadBalancerRequestBuilder id(String id) {
        reqBuilder.attr("id", id);
        return this;
    }

    public LoadBalancerRequestBuilder metadata(Map<String, String> metadata) {
        reqBuilder.attr("metadata", metadata);
        return this;
    }

    public LoadBalancerRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    public LoadBalancerRequestBuilder serviceName(String name) {
        reqBuilder.attr("service_name", name);
        return this;
    }

    public LoadBalancerRequestBuilder unhealthyThreshold(int numTimes) {
        reqBuilder.attr("unhealthy_threshold", numTimes);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
