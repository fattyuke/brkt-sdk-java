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
