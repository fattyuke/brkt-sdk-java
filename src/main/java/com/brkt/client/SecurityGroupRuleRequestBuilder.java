package com.brkt.client;

import com.brkt.client.util.RequestBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a security group rule.
 */
public class SecurityGroupRuleRequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public SecurityGroupRuleRequestBuilder cidrIp(String cidrIp) {
        reqBuilder.attr("cidr_ip", cidrIp);
        return this;
    }

    public SecurityGroupRuleRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public SecurityGroupRuleRequestBuilder ipProto(String ipProto) {
        reqBuilder.attr("ip_proto", ipProto);
        return this;
    }

    public SecurityGroupRuleRequestBuilder isIngress(boolean isIngress) {
        reqBuilder.attr("is_ingress", isIngress);
        return this;
    }

    public SecurityGroupRuleRequestBuilder portRangeFrom(int port) {
        reqBuilder.attr("port_range_from", port);
        return this;
    }

    public SecurityGroupRuleRequestBuilder portRangeTo(int port) {
        reqBuilder.attr("port_range_to", port);
        return this;
    }

    public SecurityGroupRuleRequestBuilder srcSecurityGroupId(String id) {
        reqBuilder.attr("src_security_group", id);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
