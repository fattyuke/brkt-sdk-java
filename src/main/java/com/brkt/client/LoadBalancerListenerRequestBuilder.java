package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a load balancer listener.
 */
public class LoadBalancerListenerRequestBuilder extends RequestBuilder {

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
     *
     * @throws com.brkt.client.util.RequestBuilder.MissingFieldsError
     * if any required fields are missing
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
