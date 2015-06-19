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
 * or updating a security group.
 */
public class SecurityGroupRequestBuilder {

    private final RequestBuilder reqBuilder = new RequestBuilder();

    public SecurityGroupRequestBuilder description(String description) {
        reqBuilder.attr("description", description);
        return this;
    }

    public SecurityGroupRequestBuilder metadata(Map<String, String> metadata) {
        reqBuilder.attr("metadata", metadata);
        return this;
    }

    public SecurityGroupRequestBuilder name(String name) {
        reqBuilder.attr("name", name);
        return this;
    }

    public SecurityGroupRequestBuilder requestedState(Constants.RequestedState state) {
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
