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

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

/**
 * Provides a type-safe way to assemble a request for creating
 * or updating a instance.
 */
public class InstanceRequestBuilder extends RequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public InstanceRequestBuilder cloudInitId(String id) {
        reqBuilder.attr("cloudinit", id);
        return this;
    }

    public InstanceRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public InstanceRequestBuilder encrypted(boolean encrypted) {
        reqBuilder.attr("encrypted", encrypted);
        return this;
    }

    public InstanceRequestBuilder id(String id) {
        reqBuilder.attr("id", id);
        return this;
    }

    public InstanceRequestBuilder imageDefinitionId(String id) {
        reqBuilder.attr("image_definition", id);
        return this;
    }

    public InstanceRequestBuilder loadBalancerId(String id) {
        reqBuilder.attr("load_balancer", id);
        return this;
    }

    public InstanceRequestBuilder machineTypeId(String id) {
        reqBuilder.attr("machine_type", id);
        return this;
    }

    public InstanceRequestBuilder metadata(Map<String, String> metadata) {
        reqBuilder.attr("metadata", metadata);
        return this;
    }

    public InstanceRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    public InstanceRequestBuilder securityGroupIds(Collection<String> ids) {
        reqBuilder.attr("security_groups", ids);
        return this;
    }

    public InstanceRequestBuilder workloadId(String id) {
        reqBuilder.attr("workload", id);
        return this;
    }

    /**
     * Build a {@code Map} that contains all of the added attributes.
     */
    public Map<String, Object> build() {
        return reqBuilder.build();
    }
}
