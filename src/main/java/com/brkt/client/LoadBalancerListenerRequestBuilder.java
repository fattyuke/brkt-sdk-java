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

import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a load balancer listener.
 */
public class LoadBalancerListenerRequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public LoadBalancerListenerRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public LoadBalancerListenerRequestBuilder id(String id) {
        reqBuilder.attr("id", id);
        return this;
    }

    public LoadBalancerListenerRequestBuilder instancePort(int port) {
        reqBuilder.attr("instance_port", port);
        return this;
    }

    public LoadBalancerListenerRequestBuilder instanceProtocol(Constants.Protocol protocol) {
        reqBuilder.attr("instance_protocol", protocol);
        return this;
    }

    public LoadBalancerListenerRequestBuilder isHealthCheckListener(boolean b) {
        reqBuilder.attr("is_health_check_listener", b);
        return this;
    }

    public LoadBalancerListenerRequestBuilder listenerPort(int port) {
        reqBuilder.attr("listener_port", port);
        return this;
    }

    public LoadBalancerListenerRequestBuilder listenerProtocol(Constants.Protocol protocol) {
        reqBuilder.attr("listener_protocol", protocol);
        return this;
    }

    public LoadBalancerListenerRequestBuilder loadBalancerId(String id) {
        reqBuilder.attr("load_balancer", id);
        return this;
    }

    public LoadBalancerListenerRequestBuilder stickiness(boolean b) {
        reqBuilder.attr("stickiness", b);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
